package com.chuyashkou.hotels_booking.service;

import com.chuyashkou.hotels_booking.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User save(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    void deleteById(Long id);

    List<User> findByAddressId(Long addressId);
}
