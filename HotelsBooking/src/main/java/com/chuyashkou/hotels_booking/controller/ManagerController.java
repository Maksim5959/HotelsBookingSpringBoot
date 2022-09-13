package com.chuyashkou.hotels_booking.controller;

import com.chuyashkou.hotels_booking.model.Apartment;
import com.chuyashkou.hotels_booking.model.Booking;
import com.chuyashkou.hotels_booking.model.Hotel;
import com.chuyashkou.hotels_booking.model.User;
import com.chuyashkou.hotels_booking.service.BookingService;
import com.chuyashkou.hotels_booking.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/manager")
@PreAuthorize("hasAuthority('1')")
public class ManagerController {

    private static final String USER = "user";
    private static final String BOOKINGS = "bookings";

    private final UserService userService;
    private final BookingService bookingService;

    public ManagerController(UserService userService, BookingService bookingService) {
        this.userService = userService;
        this.bookingService = bookingService;
    }

    @GetMapping("/update-hotel-data")
    public ModelAndView getUpdateHotelPage(ModelAndView modelAndView, HttpSession session) {
        modelAndView.addObject("hotel", this.getHotel(session));
        modelAndView.setViewName("manager/updateHotel");
        return modelAndView;
    }

    @GetMapping("/all-apartments")
    public ModelAndView getAllApartmentsPage(ModelAndView modelAndView, HttpSession session) {
        modelAndView.addObject("hotel", this.getHotel(session));
        modelAndView.setViewName("manager/allHotelApartments");
        return modelAndView;
    }

    @GetMapping("/add-apartment")
    public ModelAndView getAddApartmentPage(ModelAndView modelAndView) {
        modelAndView.setViewName("manager/addHotelApartment");
        return modelAndView;
    }

    @GetMapping("/hotel-bookings")
    public ModelAndView getHotelBookingsPage(ModelAndView modelAndView) {
        List<Booking> bookings = bookingService.findAll().stream().peek(booking -> {
//            booking.getUser().setPassword(null);
//            booking.getUser().setAddress(null);
//            booking.getUser().setHotel(null);
        }).collect(Collectors.toList());
        modelAndView.addObject(BOOKINGS, bookings);
        modelAndView.setViewName("manager/allHotelBookings");
        return modelAndView;
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("apartment", new Apartment());
    }

    private Hotel getHotel(HttpSession session) {
        User user = (User) session.getAttribute(USER);
        Optional<User> optionalUser = userService.findById(user.getId());
        Hotel hotel = new Hotel();
        if (optionalUser.isPresent()) {
            hotel = optionalUser.get().getHotel();
            hotel.setUser(null);
        }
        return hotel;
    }
}
