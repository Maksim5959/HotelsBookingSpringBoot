package com.chuyashkou.hotels_booking.facade.impl;

import com.chuyashkou.hotels_booking.dto.UserDto;
import com.chuyashkou.hotels_booking.facade.UserFacade;
import com.chuyashkou.hotels_booking.model.Address;
import com.chuyashkou.hotels_booking.model.Role;
import com.chuyashkou.hotels_booking.model.User;
import com.chuyashkou.hotels_booking.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserFacadeImpl(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<UserDto> findById(Long id) {
        Optional<User> optionalUser = userService.findById(id);
        return optionalUser.map(u -> modelMapper.map(u, UserDto.class));
    }

    @Override
    public Optional<UserDto> saveAdmin(User user) {
        user.setRoles(new HashSet<>(List.of(Role.USER, Role.ADMIN)));
        Optional<User> optionalUser = userService.save(user);
        return optionalUser.map(u -> modelMapper.map(u, UserDto.class));
    }

    @Override
    public Optional<UserDto> saveUser(User user) {
        user.setRoles(new HashSet<>(List.of(Role.USER)));
        Optional<User> optionalUser = userService.save(user);
        return optionalUser.map(u -> modelMapper.map(u, UserDto.class));
    }

    @Override
    public Optional<UserDto> update(User user) {
        Optional<User> optionalUser = userService.update(user);
        return optionalUser.map(u -> modelMapper.map(u, UserDto.class));
    }

    @Override
    public Optional<UserDto> deleteById(Long id) {
        Optional<User> optionalUser = userService.deleteById(id);
        return optionalUser.map(u -> modelMapper.map(u, UserDto.class));
    }

    @Override
    public List<UserDto> findAllWithoutAdmins() {
        List<User> users = userService.findAllWithoutAdmins();
        return users.stream()
                .map(u -> modelMapper.map(u, UserDto.class))
                .sorted(Comparator.comparing(UserDto::getId))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUserAddress(Long addressId) {
        userService.deleteUserAddress(addressId);
    }

    @Override
    public void saveUserAddress(Address address) {
        userService.saveUserAddress(address);
    }

    @Override
    public void updateUserPassword(String password) {
        userService.updateUserPassword(password);
    }

    @Override
    public void updateUserActiveField(Long id, boolean isActive) {
        userService.updateUserActiveField(id, isActive);
    }
}
