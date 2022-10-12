package com.chuyashkou.hotels_booking.controller;

import com.chuyashkou.hotels_booking.facade.BookingFacade;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BookingController {

    private final BookingFacade bookingFacade;

    public BookingController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @GetMapping("/manager/hotel-bookings/confirm/{id}")
    public ModelAndView confirmBooking(ModelAndView modelAndView, @PathVariable Long id) {
        bookingFacade.confirmBooking(id);
        modelAndView.setViewName("redirect:/manager/hotel-bookings");
        return modelAndView;
    }

    @GetMapping("/manager/hotel-bookings/{id}")
    public ModelAndView deleteBooking(@PathVariable Long id, ModelAndView modelAndView) {
        bookingFacade.deleteById(id);
        modelAndView.setViewName("redirect:/manager/hotel-bookings");
        return modelAndView;
    }
}
