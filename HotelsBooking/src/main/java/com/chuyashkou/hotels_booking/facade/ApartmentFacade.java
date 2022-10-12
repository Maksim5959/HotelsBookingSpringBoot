package com.chuyashkou.hotels_booking.facade;

import com.chuyashkou.hotels_booking.model.Apartment;

import java.util.Optional;

public interface ApartmentFacade {

    Optional<Apartment> save(Apartment apartment);

    Optional<Apartment> update (Apartment apartment);

    boolean deleteById(Long id);
}
