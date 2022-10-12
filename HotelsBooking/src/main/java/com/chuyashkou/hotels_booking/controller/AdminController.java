package com.chuyashkou.hotels_booking.controller;

import com.chuyashkou.hotels_booking.dto.HotelDto;
import com.chuyashkou.hotels_booking.dto.UserDto;
import com.chuyashkou.hotels_booking.facade.HotelFacade;
import com.chuyashkou.hotels_booking.facade.UserFacade;
import com.chuyashkou.hotels_booking.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('2')")
public class AdminController {

    private static final String USER = "user";
    private static final String USERS = "users";
    private static final String HOTELS = "hotels";

    private final UserFacade userFacade;
    private final HotelFacade hotelFacade;

    public AdminController(UserFacade userFacade, HotelFacade hotelFacade) {
        this.userFacade = userFacade;
        this.hotelFacade = hotelFacade;
    }

    @GetMapping("/all-users")
    public ModelAndView getAllUsersPage(ModelAndView modelAndView) {
        List<UserDto> users = userFacade.findAllWithoutAdmins();
        modelAndView.setViewName("admin/allUsers");
        modelAndView.addObject(USERS, users);
        return modelAndView;
    }

    @GetMapping("/all-hotels")
    public ModelAndView getAllHotelsPage(ModelAndView modelAndView) {
        List<HotelDto> hotels = hotelFacade.findAll();
        modelAndView.addObject(HOTELS, hotels);
        modelAndView.setViewName("admin/allHotels");
        return modelAndView;
    }

    @GetMapping("/add-new-admin")
    public ModelAndView getAddNewAdminPage(ModelAndView modelAndView) {
        modelAndView.setViewName("admin/addNewAdmin");
        return modelAndView;
    }

    @GetMapping("/all-users/{id}/{active}")
    public ModelAndView updateUserActiveField(@PathVariable Long id, @PathVariable boolean active, ModelAndView modelAndView) {
        userFacade.updateUserActiveField(id, active);
        modelAndView.setViewName("redirect:/admin/all-users");
        return modelAndView;
    }

    @GetMapping("/all-hotels/{id}/{registered}")
    public ModelAndView updateHotelRegistrationField(@PathVariable Long id, @PathVariable boolean registered, ModelAndView modelAndView) {
        hotelFacade.updateRegistrationField(id, registered);
        modelAndView.setViewName("redirect:/admin/all-hotels");
        return modelAndView;
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute(USER, new User());
    }
}
