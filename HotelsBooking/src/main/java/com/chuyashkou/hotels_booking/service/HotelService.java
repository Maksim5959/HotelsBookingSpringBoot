package com.chuyashkou.hotels_booking.service;

import com.chuyashkou.hotels_booking.model.Hotel;

import java.util.Optional;

public interface HotelService {

    Hotel save(Hotel hotel);

    Optional<Hotel> findByLegalName(String legalName);
}
