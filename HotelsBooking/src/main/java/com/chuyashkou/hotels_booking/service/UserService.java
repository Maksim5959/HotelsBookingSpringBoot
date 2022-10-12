package com.chuyashkou.hotels_booking.service;

import com.chuyashkou.hotels_booking.dto.UserDto;
import com.chuyashkou.hotels_booking.model.Address;
import com.chuyashkou.hotels_booking.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findById(Long id);

    Optional<User> save(User user);

    Optional<User> update(User user);

    Optional<User> deleteById(Long id);

    List<User> findAllWithoutAdmins();

    void deleteUserAddress(Long addressId);

    void saveUserAddress(Address address);

    void updateUserPassword (String password);

    void updateUserActiveField (Long id, boolean isActive);
}
