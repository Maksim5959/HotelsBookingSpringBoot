package com.chuyashkou.hotels_booking.repository;

import com.chuyashkou.hotels_booking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
