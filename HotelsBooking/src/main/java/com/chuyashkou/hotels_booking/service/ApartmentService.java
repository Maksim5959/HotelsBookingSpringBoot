package com.chuyashkou.hotels_booking.service;

import com.chuyashkou.hotels_booking.model.Apartment;

import java.util.Optional;

public interface ApartmentService {

    Optional<Apartment> save(Apartment apartment);

    Optional<Apartment> update (Apartment apartment);

    boolean deleteById(Long id);
}
