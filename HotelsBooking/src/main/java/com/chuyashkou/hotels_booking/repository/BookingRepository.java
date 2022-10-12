package com.chuyashkou.hotels_booking.repository;

import com.chuyashkou.hotels_booking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserId (Long userId);

    List<Booking> findByApartmentId(Long apartmentId);
}
