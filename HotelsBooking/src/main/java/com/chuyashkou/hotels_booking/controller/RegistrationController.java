package com.chuyashkou.hotels_booking.controller;

import com.chuyashkou.hotels_booking.facade.UserFacade;
import com.chuyashkou.hotels_booking.model.Role;
import com.chuyashkou.hotels_booking.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private static final String USER = "user";
    private static final String USER_SAVE_ERROR = "userSaveError";
    private static final boolean TRUE_FLAG = true;

    private final UserFacade userFacade;

    public RegistrationController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @GetMapping
    public ModelAndView getRegistrationPage(ModelAndView modelAndView) {
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping
    public ModelAndView saveUser(@ModelAttribute User user, ModelAndView modelAndView) {
        if (userFacade.saveUser(user).isPresent()) {
            modelAndView.setViewName("redirect:/login");
        } else {
            modelAndView.addObject(USER_SAVE_ERROR, TRUE_FLAG);
            modelAndView.setViewName("registration");
        }
        return modelAndView;
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute(USER, new User());
    }
}
