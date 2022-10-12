package com.chuyashkou.hotels_booking.service;

import com.chuyashkou.hotels_booking.model.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingService {

    Optional<Booking> findById(Long id);

    Optional <Booking> save(Booking booking);

    List<Booking> findByHotelId(Long hotelId);

    List<Booking> findByUserId(Long userId);

    void deleteById(Long id);

    void confirmBooking (Long id);
}
