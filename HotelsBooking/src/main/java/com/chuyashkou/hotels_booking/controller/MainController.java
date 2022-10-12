package com.chuyashkou.hotels_booking.controller;

import com.chuyashkou.hotels_booking.model.HotelsSearchData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class MainController {

    private static final String HOTELS_SEARCH_DATA = "searchData";

    @GetMapping
    public ModelAndView getMainPage(ModelAndView modelAndView) {
        modelAndView.setViewName("main");
        return modelAndView;
    }

    @ModelAttribute
    public void addAttributes(Model model){
        model.addAttribute(HOTELS_SEARCH_DATA, new HotelsSearchData());
    }
}
