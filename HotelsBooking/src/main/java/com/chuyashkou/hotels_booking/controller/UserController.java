package com.chuyashkou.hotels_booking.controller;

import com.chuyashkou.hotels_booking.model.Address;
import com.chuyashkou.hotels_booking.model.User;
import com.chuyashkou.hotels_booking.service.AddressService;
import com.chuyashkou.hotels_booking.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('0')")
public class UserController {

    private static final String SAVE_ERROR = "saveError";
    private static final String SAVE_SUCCESS = "saveSuccess";
    private static final String DELETE_ERROR = "deleteError";
    private static final boolean TRUE_FLAG = true;
    private static final String USER = "user";
    private static final String ADDRESS = "address";

    private final UserService userService;
    private final AddressService addressService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, AddressService addressService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.addressService = addressService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/personal-details")
    public ModelAndView getPersonalDetailsPage(ModelAndView modelAndView, HttpSession session) {
        User user = (User) session.getAttribute(USER);
        modelAndView.addObject(USER, user);
        modelAndView.setViewName("user/personalDetails");
        return modelAndView;
    }

    @GetMapping("/local-data")
    public ModelAndView getLocalDataPage(ModelAndView modelAndView, HttpSession session) {
        User user = (User) session.getAttribute(USER);
        if (user.getAddress() != null) modelAndView.addObject(ADDRESS, user.getAddress());
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
//        User user = (User) session.getAttribute(USER);
//        List<User> users = null;
//        if (Objects.nonNull(user.getAddress())) users = userService.findByAddressId(user.getAddress().getId());
//        Optional<User> optionalUser = userService.findById(id);
//        if (optionalUser.isPresent() && optionalUser.get().getBookings().isEmpty()) {
//            if (Objects.nonNull(users) && users.size() > 1) {
//                userService.deleteById(id);
//                session.invalidate();
//                modelAndView.setViewName("redirect:/login");
//            } else {
//                userService.deleteById(id);
//                addressService.deleteById(user.getAddress().getId());
//                session.invalidate();
//                modelAndView.setViewName("redirect:/login");
//            }
//        } else {
//            modelAndView.addObject(USER, user);
//            modelAndView.setViewName("user/personalDetails");
//            modelAndView.addObject(DELETE_ERROR, TRUE_FLAG);
//        }
        return modelAndView;
    }

    @GetMapping("/local-data/{id}")
    public ModelAndView deleteUserAddress(@PathVariable Long id, ModelAndView modelAndView, HttpSession session) {
        User user = (User) session.getAttribute(USER);
        Optional<User> optionalUser = userService.findByEmail(user.getEmail());
        List<User> users = userService.findByAddressId(id);
        User saveUser = null;
        if (optionalUser.isEmpty()) {
            modelAndView.addObject(DELETE_ERROR, TRUE_FLAG);
            modelAndView.setViewName("user/localData");
            return modelAndView;
        } else {
            user = optionalUser.get();
        }
        if (users.size() > 1) {
            user.setAddress(null);
            saveUser = userService.save(user);
        } else if (users.size() == 1) {
            user.setAddress(null);
            saveUser = userService.save(user);
            addressService.deleteById(id);
        } else {
            modelAndView.addObject(DELETE_ERROR, TRUE_FLAG);
        }
        if (saveUser != null) {
            saveUser.setPassword(null);
            session.setAttribute(USER, saveUser);
        } else {
            modelAndView.addObject(DELETE_ERROR, TRUE_FLAG);
        }
        modelAndView.setViewName("user/localData");
        return modelAndView;
    }

    @PostMapping("/personal-details")
    public ModelAndView updateUserData(@ModelAttribute User user, ModelAndView modelAndView, HttpSession session) {
        Optional<User> oldUser = userService.findById(user.getId());
        User saveUser = null;
        if (oldUser.isPresent()) {
            user.setPassword(oldUser.get().getPassword());
            user.setAddress(oldUser.get().getAddress());
            saveUser = userService.save(user);
        } else {
            modelAndView.addObject(SAVE_ERROR, TRUE_FLAG);
        }
        if (Objects.nonNull(saveUser)) {
            saveUser.setPassword(null);
            session.setAttribute(USER, saveUser);
        } else {
            modelAndView.addObject(SAVE_ERROR, TRUE_FLAG);
        }
        modelAndView.setViewName("user/personalDetails");
        return modelAndView;
    }

    @PostMapping("/local-data")
    public ModelAndView saveUserAddress(@ModelAttribute Address address, ModelAndView modelAndView, HttpSession session) {
        User user = (User) session.getAttribute(USER);
        Optional<Address> addressWithId = getAddressWithId(address);
        Optional<User> optionalUser = userService.findByEmail(user.getEmail());
        User saveUser;
        if (optionalUser.isEmpty()) {
            modelAndView.addObject(SAVE_ERROR, TRUE_FLAG);
            modelAndView.setViewName("user/localData");
            return modelAndView;
        } else {
            user = optionalUser.get();
        }
        if (addressWithId.isPresent()) {
            user.setAddress(addressWithId.get());
        } else {
            user.setAddress(address);
        }
        saveUser = userService.save(user);
        if (saveUser != null) {
            saveUser.setPassword(null);
            session.setAttribute(USER, saveUser);
        } else {
            modelAndView.addObject(SAVE_ERROR, TRUE_FLAG);
        }
        modelAndView.setViewName("redirect:/user/local-data");
        return modelAndView;
    }

    @PostMapping("/security")
    public ModelAndView savePassword(@RequestParam("password") String password, ModelAndView modelAndView, HttpSession session) {
        User user = (User) session.getAttribute(USER);
        user.setPassword(passwordEncoder.encode(password));
        User saveUser = userService.save(user);
        if (Objects.nonNull(saveUser)) {
            modelAndView.addObject(SAVE_SUCCESS, TRUE_FLAG);
            session.setAttribute(USER, saveUser);
        } else {
            modelAndView.addObject(SAVE_ERROR, TRUE_FLAG);
        }
        modelAndView.setViewName("user/security");
        return modelAndView;
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute(ADDRESS, new Address());
    }

    private Optional<Address> getAddressWithId(Address address) {
        return addressService.findByCountryAndCityAndStreetAndHouseAndBuildingAndApartmentNumber
                (address.getCountry(), address.getCity(), address.getStreet(),
                        address.getHouse(), address.getBuilding(), address.getApartmentNumber());
    }
}
