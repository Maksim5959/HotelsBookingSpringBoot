package com.chuyashkou.hotels_booking.controller;

import com.chuyashkou.hotels_booking.dto.UserDto;
import com.chuyashkou.hotels_booking.facade.HotelFacade;
import com.chuyashkou.hotels_booking.facade.UserFacade;
import com.chuyashkou.hotels_booking.model.Hotel;
import com.chuyashkou.hotels_booking.model.Role;
import com.chuyashkou.hotels_booking.model.User;
import com.chuyashkou.hotels_booking.util.SecurityContextHandler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@PreAuthorize("hasAuthority('0')")
public class HotelRegistrationController {

    private static final String USER = "user";
    private static final String HOTEL = "hotel";
    private static final String SAVE_ERROR = "saveError";
    private static final String USER_IS_NULL_MESSAGE = "User with id %s is null!";
    private static final boolean TRUE_FLAG = true;

    private final HotelFacade hotelFacade;
    private final UserFacade userFacade;

    public HotelRegistrationController(HotelFacade hotelFacade, UserFacade userFacade) {
        this.hotelFacade = hotelFacade;
        this.userFacade = userFacade;
    }

    @GetMapping("/user/hotel-registration")
    public ModelAndView getHotelRegistrationPage(ModelAndView modelAndView) {
        Optional<UserDto> optionalUserDto = userFacade.findById(SecurityContextHandler.getUserId());
        optionalUserDto.ifPresent(userDto -> {
            if (userDto.getRoles().contains(Role.MANAGER) || userDto.getRoles().contains(Role.ADMIN)) {
                modelAndView.setViewName("redirect:/");
            } else {
                modelAndView.addObject(USER, userDto);
                modelAndView.setViewName("user/hotelRegistration");
            }
        });
        return modelAndView;
    }

    @PostMapping("/user/hotel-registration")
    public ModelAndView registerHotel(@ModelAttribute Hotel hotel, ModelAndView modelAndView) {
        Optional<UserDto> optionalUserDto = userFacade.findById(SecurityContextHandler.getUserId());
        if (hotelFacade.save(hotel).isPresent()) {
            modelAndView.setViewName("redirect:/manager/update-hotel-data");
        } else {
            modelAndView.addObject(USER, optionalUserDto.orElseThrow(() ->
                    new RuntimeException(String.format(USER_IS_NULL_MESSAGE, SecurityContextHandler.getUserId()))));
            modelAndView.addObject(SAVE_ERROR, TRUE_FLAG);
            modelAndView.setViewName("user/hotelRegistration");
        }
        return modelAndView;
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute(USER, new User());
        model.addAttribute(HOTEL, new Hotel());
    }
}
