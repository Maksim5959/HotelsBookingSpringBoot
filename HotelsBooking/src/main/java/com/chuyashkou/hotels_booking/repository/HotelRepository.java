package com.chuyashkou.hotels_booking.repository;

import com.chuyashkou.hotels_booking.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    Optional<Hotel> findByLegalName(String legalName);

    Optional<Hotel> findByAddressId (Long addressId);

    Optional<Hotel> findByUserId (Long userId);
}
