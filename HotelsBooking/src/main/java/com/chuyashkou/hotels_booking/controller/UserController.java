package com.chuyashkou.hotels_booking.controller;

import com.chuyashkou.hotels_booking.dto.UserDto;
import com.chuyashkou.hotels_booking.facade.AddressFacade;
import com.chuyashkou.hotels_booking.facade.UserFacade;
import com.chuyashkou.hotels_booking.model.Address;
import com.chuyashkou.hotels_booking.model.User;
import com.chuyashkou.hotels_booking.util.SecurityContextHandler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('0')")
public class UserController {

    private static final String USER = "user";
    private static final String ADDRESS = "address";
    private static final String SAVE_ERROR = "saveError";
    private static final String SAVE_SUCCESS = "saveSuccess";
    private static final String DELETE_ERROR = "deleteError";
    private static final String USER_IS_NULL_MESSAGE = "User with id %s is null!";
    private static final boolean TRUE_FLAG = true;

    private final UserFacade userFacade;
    private final AddressFacade addressFacade;

    public UserController(UserFacade userFacade, AddressFacade addressFacade) {
        this.userFacade = userFacade;
        this.addressFacade = addressFacade;
    }

    @GetMapping("/personal-details")
    public ModelAndView getPersonalDetailsPage(ModelAndView modelAndView) {
        Long userId = SecurityContextHandler.getUserId();
        Optional<UserDto> optionalUserDto = userFacade.findById(userId);
        modelAndView.addObject(USER, optionalUserDto.orElseThrow(() ->
                new RuntimeException(String.format(USER_IS_NULL_MESSAGE, userId))));
        modelAndView.setViewName("user/personalDetails");
        return modelAndView;
    }

    @GetMapping("/local-data")
    public ModelAndView getLocalDataPage(ModelAndView modelAndView) {
        Optional<Address> optionalAddress = addressFacade.findByUserId(SecurityContextHandler.getUserId());
        optionalAddress.ifPresent(address -> modelAndView.addObject(ADDRESS, address));
        modelAndView.setViewName("user/localData");
        return modelAndView;
    }

    @GetMapping("/security")
    public ModelAndView getSecurityPage(ModelAndView modelAndView) {
        modelAndView.setViewName("user/security");
        return modelAndView;
    }

    @GetMapping("/personal-details/{id}")
    public ModelAndView deleteUser(@PathVariable Long id, ModelAndView modelAndView, HttpSession session) {
        Optional<UserDto> optionalUserDto = userFacade.deleteById(id);
        if (optionalUserDto.isEmpty()) {
            session.invalidate();
            modelAndView.setViewName("redirect:/login");
        } else {
            modelAndView.addObject(USER, optionalUserDto.get());
            modelAndView.addObject(DELETE_ERROR, TRUE_FLAG);
            modelAndView.setViewName("user/personalDetails");
        }
        return modelAndView;
    }

    @GetMapping("/local-data/{id}")
    public ModelAndView deleteUserAddress(@PathVariable Long id, ModelAndView modelAndView) {
        userFacade.deleteUserAddress(id);
        modelAndView.setViewName("redirect:/user/local-data");
        return modelAndView;
    }

    @PostMapping("/personal-details")
    public ModelAndView updateUserData(@ModelAttribute User user, ModelAndView modelAndView) {
        if (userFacade.update(user).isPresent()) {
            modelAndView.setViewName("redirect:/user/personal-details");
        } else {
            modelAndView.setViewName("user/personalDetails");
            modelAndView.addObject(USER, user);
            modelAndView.addObject(SAVE_ERROR, TRUE_FLAG);
        }
        return modelAndView;
    }

    @PostMapping("/local-data")
    public ModelAndView saveUserAddress(@ModelAttribute Address address, ModelAndView modelAndView) {
        userFacade.saveUserAddress(address);
        modelAndView.setViewName("redirect:/user/local-data");
        return modelAndView;
    }

    @PostMapping("/security")
    public ModelAndView savePassword(@RequestParam String password, ModelAndView modelAndView) {
        userFacade.updateUserPassword(password);
        modelAndView.addObject(SAVE_SUCCESS, TRUE_FLAG);
        modelAndView.setViewName("user/security");
        return modelAndView;
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute(ADDRESS, new Address());
        model.addAttribute(USER, new User());
    }
}
