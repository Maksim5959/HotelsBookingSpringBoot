package com.chuyashkou.hotels_booking.controller;

import com.chuyashkou.hotels_booking.dto.BookingDto;
import com.chuyashkou.hotels_booking.dto.HotelDto;
import com.chuyashkou.hotels_booking.facade.BookingFacade;
import com.chuyashkou.hotels_booking.facade.HotelFacade;
import com.chuyashkou.hotels_booking.model.Apartment;
import com.chuyashkou.hotels_booking.util.SecurityContextHandler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/manager")
@PreAuthorize("hasAuthority('1')")
public class ManagerController {

    private static final String APARTMENT = "apartment";
    private static final String HOTEL = "hotel";
    private static final String BOOKINGS = "bookings";
    private static final String HOTEL_IS_NULL_MESSAGE = "user's hotel with id %s is null";

    private final HotelFacade hotelFacade;
    private final BookingFacade bookingFacade;

    public ManagerController(HotelFacade hotelFacade, BookingFacade bookingFacade) {
        this.hotelFacade = hotelFacade;
        this.bookingFacade = bookingFacade;
    }

    @GetMapping("/update-hotel-data")
    public ModelAndView getUpdateHotelPage(ModelAndView modelAndView) {
        Long userId = SecurityContextHandler.getUserId();
        HotelDto hotelDto = hotelFacade.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException(String.format(HOTEL_IS_NULL_MESSAGE, userId)));
        if (hotelDto.isRegistered()) {
            modelAndView.addObject(HOTEL, hotelDto);
            modelAndView.setViewName("manager/updateHotel");
        } else {
            modelAndView.setViewName("manager/waitingPage");
        }
        return modelAndView;
    }

    @GetMapping("/all-apartments")
    public ModelAndView getAllApartmentsPage(ModelAndView modelAndView) {
        Long userId = SecurityContextHandler.getUserId();
        HotelDto hotelDto = hotelFacade.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException(String.format(HOTEL_IS_NULL_MESSAGE, userId)));
        if (hotelDto.isRegistered()) {
            modelAndView.addObject(HOTEL, hotelDto);
            modelAndView.setViewName("manager/allHotelApartments");
        } else {
            modelAndView.setViewName("manager/waitingPage");
        }
        return modelAndView;
    }

    @GetMapping("/add-apartment")
    public ModelAndView getAddApartmentPage(ModelAndView modelAndView) {
        Long userId = SecurityContextHandler.getUserId();
        HotelDto hotelDto = hotelFacade.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException(String.format(HOTEL_IS_NULL_MESSAGE, userId)));
        if (hotelDto.isRegistered()) {
            modelAndView.setViewName("manager/addHotelApartment");
        } else {
            modelAndView.setViewName("manager/waitingPage");
        }
        return modelAndView;
    }

    @GetMapping("/hotel-bookings")
    public ModelAndView getHotelBookingsPage(ModelAndView modelAndView) {
        Long userId = SecurityContextHandler.getUserId();
        HotelDto hotelDto = hotelFacade.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException(String.format(HOTEL_IS_NULL_MESSAGE, userId)));
        List<BookingDto> bookingsDto = bookingFacade.findByHotelId(hotelDto.getId());
        if (hotelDto.isRegistered()) {
            modelAndView.addObject(BOOKINGS, bookingsDto);
            modelAndView.setViewName("manager/allHotelBookings");
        } else {
            modelAndView.setViewName("manager/waitingPage");
        }
        return modelAndView;
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute(APARTMENT, new Apartment());
    }
}
