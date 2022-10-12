package com.chuyashkou.hotels_booking.controller;

import com.chuyashkou.hotels_booking.dto.HotelDto;
import com.chuyashkou.hotels_booking.facade.HotelFacade;
import com.chuyashkou.hotels_booking.model.Hotel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class HotelController {

    private static final String DELETE_ERROR = "deleteError";
    private static final String HOTEL = "hotel";
    private static final String SAVE_ERROR = "saveError";
    private static final boolean TRUE_FLAG = true;

    private final HotelFacade hotelFacade;

    public HotelController(HotelFacade hotelFacade) {
        this.hotelFacade = hotelFacade;
    }

    @GetMapping("/manager/update-hotel-data/{id}")
    @PreAuthorize("hasAuthority('1')")
    public ModelAndView deleteHotel(@PathVariable Long id, ModelAndView modelAndView) {
        Optional<HotelDto> optionalHotelDto = hotelFacade.delete(id);
        if (optionalHotelDto.isEmpty()) {
            modelAndView.setViewName("redirect:/");
        } else {
            modelAndView.addObject(HOTEL, optionalHotelDto.get());
            modelAndView.addObject(DELETE_ERROR, TRUE_FLAG);
            modelAndView.setViewName("manager/updateHotel");
        }
        return modelAndView;
    }

    @PostMapping("/manager/update-hotel-data")
    @PreAuthorize("hasAuthority('1')")
    public ModelAndView updateHotelData(@ModelAttribute Hotel hotel, ModelAndView modelAndView) {
        if (hotelFacade.update(hotel).isPresent()) {
            modelAndView.setViewName("redirect:/manager/update-hotel-data");
        } else {
            modelAndView.addObject(SAVE_ERROR, TRUE_FLAG);
            modelAndView.addObject(HOTEL, hotel);
            modelAndView.setViewName("manager/updateHotel");
        }
        return modelAndView;
    }
}
