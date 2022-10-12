package com.chuyashkou.hotels_booking.controller;

import com.chuyashkou.hotels_booking.dto.HotelDto;
import com.chuyashkou.hotels_booking.facade.ApartmentFacade;
import com.chuyashkou.hotels_booking.facade.HotelFacade;
import com.chuyashkou.hotels_booking.model.Apartment;
import com.chuyashkou.hotels_booking.model.Comfort;
import com.chuyashkou.hotels_booking.util.SecurityContextHandler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@PreAuthorize("hasAuthority('1')")
public class ApartmentController {

    private static final String APARTMENT = "apartment";
    private static final String COMFORT = "comfort";
    private static final String HOTEL = "hotel";
    private static final String DELETE_ERROR = "deleteError";
    private static final String SAVE_ERROR = "saveError";
    private static final String SAVE_SUCCESS = "saveSuccess";
    private static final boolean TRUE_FLAG = true;

    private final HotelFacade hotelFacade;
    private final ApartmentFacade apartmentFacade;

    public ApartmentController(HotelFacade hotelFacade, ApartmentFacade apartmentFacade) {
        this.hotelFacade = hotelFacade;
        this.apartmentFacade = apartmentFacade;
    }

    @GetMapping("/manager/all-apartments/{id}")
    public ModelAndView deleteApartment(@PathVariable Long id, ModelAndView modelAndView) {
        if (apartmentFacade.deleteById(id)) {
            modelAndView.setViewName("redirect:/manager/all-apartments");
        } else {
            this.ifError(modelAndView, DELETE_ERROR);
        }
        return modelAndView;
    }

    @PostMapping("/manager/add-apartment")
    public ModelAndView addApartment(@ModelAttribute Apartment apartment, ModelAndView modelAndView) {
        hotelFacade.addNewApartment(apartment);
        modelAndView.setViewName("manager/addHotelApartment");
        modelAndView.addObject(SAVE_SUCCESS, TRUE_FLAG);
        return modelAndView;
    }

    @PostMapping("/manager/all-apartments")
    public ModelAndView updateApartmentData(@ModelAttribute Apartment apartment, ModelAndView modelAndView) {
        if (apartmentFacade.update(apartment).isPresent()) {
            modelAndView.setViewName("redirect:/manager/all-apartments");
        } else {
            this.ifError(modelAndView, SAVE_ERROR);
        }
        return modelAndView;
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute(APARTMENT, new Apartment());
        model.addAttribute(COMFORT, new Comfort());
    }

    private void ifError(ModelAndView modelAndView, String errorName) {
        Optional<HotelDto> optionalHotelDto = hotelFacade.findByUserId(SecurityContextHandler.getUserId());
        optionalHotelDto.ifPresent(hotelDto -> {
            if (hotelDto.isRegistered()) {
                modelAndView.addObject(HOTEL, hotelDto);
                modelAndView.addObject(errorName, TRUE_FLAG);
                modelAndView.setViewName("manager/allHotelApartments");
            } else {
                modelAndView.setViewName("manager/waitingPage");
            }
        });
    }
}
