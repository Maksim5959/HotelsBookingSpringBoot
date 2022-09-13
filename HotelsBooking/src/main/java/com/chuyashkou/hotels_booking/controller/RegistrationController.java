package com.chuyashkou.hotels_booking.controller;

import com.chuyashkou.hotels_booking.model.Role;
import com.chuyashkou.hotels_booking.model.User;
import com.chuyashkou.hotels_booking.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private static final String USER = "user";
    private static final String USER_SAVE_ERROR = "userSaveError";

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public RegistrationController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView getRegistrationPage(ModelAndView modelAndView) {
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping
    public ModelAndView saveUser(@ModelAttribute User user, ModelAndView modelAndView) {
        Optional<User> userByEmail = userService.findByEmail(user.getEmail());
        if (userByEmail.isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActive(true);
            user.setRoles(new HashSet<>(List.of(Role.USER)));
            User savedUser = userService.save(user);
            modelAndView.addObject(USER, savedUser);
            modelAndView.setViewName("redirect:/login");
        } else {
            modelAndView.addObject(USER_SAVE_ERROR, true);
            modelAndView.setViewName("registration");
        }
        return modelAndView;
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute(USER, new User());
    }
}
