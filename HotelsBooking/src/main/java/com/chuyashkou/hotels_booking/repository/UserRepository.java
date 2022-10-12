package com.chuyashkou.hotels_booking.repository;

import com.chuyashkou.hotels_booking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);

    List<User> findByAddressId(Long addressId);

    void deleteById(@NonNull Long id);
}
