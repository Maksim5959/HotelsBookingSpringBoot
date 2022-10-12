package com.chuyashkou.hotels_booking.facade;

import com.chuyashkou.hotels_booking.dto.BookingDto;
import com.chuyashkou.hotels_booking.model.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingFacade {

    Optional<BookingDto> findById(Long id);

    Optional <BookingDto> save(Booking booking);

    List<BookingDto> findByHotelId(Long hotelId);

    List<BookingDto> findByUserId(Long userId);

    void deleteById(Long id);

    void confirmBooking (Long id);
}
