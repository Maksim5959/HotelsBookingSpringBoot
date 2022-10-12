package com.chuyashkou.hotels_booking.facade;

import com.chuyashkou.hotels_booking.dto.UserDto;
import com.chuyashkou.hotels_booking.model.Address;
import com.chuyashkou.hotels_booking.model.User;

import java.util.List;
import java.util.Optional;

public interface UserFacade {

    Optional<UserDto> findById(Long id);

    Optional<UserDto> saveAdmin(User user);

    Optional<UserDto> saveUser(User user);

    Optional<UserDto> update(User user);

    Optional<UserDto> deleteById(Long id);

    List<UserDto> findAllWithoutAdmins();

    void deleteUserAddress(Long addressId);

    void saveUserAddress(Address address);

    void updateUserPassword (String password);

    void updateUserActiveField (Long id, boolean isActive);
}
