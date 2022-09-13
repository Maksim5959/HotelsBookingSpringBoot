package com.chuyashkou.hotels_booking.controller;

import com.chuyashkou.hotels_booking.model.Address;
import com.chuyashkou.hotels_booking.model.Hotel;
import com.chuyashkou.hotels_booking.model.Role;
import com.chuyashkou.hotels_booking.model.User;
import com.chuyashkou.hotels_booking.service.AddressService;
import com.chuyashkou.hotels_booking.service.HotelService;
import com.chuyashkou.hotels_booking.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class HotelController {

    private static final String USER = "user";
    private static final String HOTEL = "hotel";
    private static final String SAVE_ERROR = "saveError";
    private static final boolean TRUE_FLAG = true;

    private final HotelService hotelService;
    private final AddressService addressService;
    private final UserService userService;

    public HotelController(HotelService hotelService, AddressService addressService, UserService userService) {
        this.hotelService = hotelService;
        this.addressService = addressService;
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('0')")
    @GetMapping("/user/hotel-registration")
    public ModelAndView getHotelRegistrationPage(ModelAndView modelAndView, HttpSession session) {
        User user = (User) session.getAttribute(USER);
        if (user.getRoles().contains(Role.MANAGER)) {
            modelAndView.setViewName("redirect:/");
        } else {
            modelAndView.setViewName("user/hotelRegistration");
        }
        return modelAndView;
    }

    @PreAuthorize("hasAuthority('0')")
    @PostMapping("/user/hotel-registration")
    public ModelAndView registerHotel(@ModelAttribute Hotel hotel, ModelAndView modelAndView, HttpSession session) {
        User sessionUser = (User) session.getAttribute(USER);
        Optional<User> optionalUser = userService.findById(sessionUser.getId());
        User savedUser = null;
        Hotel savedHotel = null;
        if (nonDuplicateLegalNameAndAddress(hotel) && optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setRoles(new HashSet<>(List.of(Role.USER, Role.MANAGER)));
            savedUser = userService.save(user);
        }
        if (Objects.nonNull(savedUser)) {
            hotel.setUser(savedUser);
            savedHotel = hotelService.save(hotel);
        }
        if (Objects.nonNull(savedHotel)) {
            savedUser.setPassword(null);
            session.setAttribute(USER, savedUser);
            modelAndView.setViewName("redirect:/");
        } else {
            modelAndView.addObject(SAVE_ERROR, TRUE_FLAG);
            modelAndView.setViewName("user/hotelRegistration");
        }
        return modelAndView;
    }

    @GetMapping("/test")
    public String test (){
        Optional<User> byEmail = userService.findByEmail("madmax@gmail.com");
        User user = byEmail.get();
        System.out.println(user);
        return "test";
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute(HOTEL, new Hotel());
    }

    private boolean nonDuplicateLegalNameAndAddress(Hotel hotel) {
        Address address = hotel.getAddress();
        Optional<Address> optionalAddress = addressService.findByCountryAndCityAndStreetAndHouseAndBuildingAndApartmentNumber
                (address.getCountry(), address.getCity(), address.getStreet(),
                        address.getHouse(), address.getBuilding(), address.getApartmentNumber());
        Optional<Hotel> optionalHotel = hotelService.findByLegalName(hotel.getLegalName());
        return optionalAddress.isEmpty() && optionalHotel.isEmpty();
    }
}
