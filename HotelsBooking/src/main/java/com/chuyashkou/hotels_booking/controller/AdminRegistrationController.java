package com.chuyashkou.hotels_booking.controller;

import com.chuyashkou.hotels_booking.facade.UserFacade;
import com.chuyashkou.hotels_booking.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('2')")
public class AdminRegistrationController {

    private static final String SAVE_ERROR = "saveError";
    private static final String SAVE_SUCCESS = "saveSuccess";
    private static final boolean TRUE_FLAG = true;

    private final UserFacade userFacade;

    public AdminRegistrationController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @PostMapping("/add-new-admin")
    public ModelAndView getAddNewAdminPage(@ModelAttribute User user, ModelAndView modelAndView) {
        if (userFacade.saveAdmin(user).isPresent()) {
            modelAndView.addObject(SAVE_SUCCESS, TRUE_FLAG);
        } else {
            modelAndView.addObject(SAVE_ERROR, TRUE_FLAG);
        }
        modelAndView.setViewName("admin/addNewAdmin");
        return modelAndView;
    }
}
