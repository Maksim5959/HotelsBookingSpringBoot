package com.chuyashkou.hotels_booking.controller;

import com.chuyashkou.hotels_booking.dto.HotelDto;
import com.chuyashkou.hotels_booking.facade.HotelFacade;
import com.chuyashkou.hotels_booking.model.Hotel;
import com.chuyashkou.hotels_booking.model.HotelsSearchData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class SearchHotelsController {

    private static final String HOTELS_SEARCH_DATA = "searchData";
    private static final String HOTELS = "hotels";
    private static final String HOTEL = "hotel";

    private final HotelFacade hotelFacade;

    public SearchHotelsController(HotelFacade hotelFacade) {
        this.hotelFacade = hotelFacade;
    }

    @GetMapping("/hotels-search-results/{id}")
    public ModelAndView getSelectedHotelPage(@PathVariable Long id, ModelAndView modelAndView){
        Optional<HotelDto> optionalHotelDto = hotelFacade.findById(id);
        optionalHotelDto.ifPresent(hotelDto -> modelAndView.addObject(HOTEL, hotelDto));
        modelAndView.setViewName("selectedHotel");
        return modelAndView;
    }

    @PostMapping("/hotels-search-results")
    public ModelAndView searchHotelsByHotelSearchData (@ModelAttribute HotelsSearchData searchData, ModelAndView modelAndView){
        List<HotelDto> hotels = hotelFacade.findByHotelSearchData(searchData);
        modelAndView.addObject(HOTELS, hotels);
        modelAndView.addObject(HOTELS_SEARCH_DATA, searchData);
        modelAndView.setViewName("hotelsSearchResults");
        return modelAndView;
    }
}
