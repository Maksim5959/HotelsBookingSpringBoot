package com.chuyashkou.hotels_booking.facade.impl;

import com.chuyashkou.hotels_booking.facade.ApartmentFacade;
import com.chuyashkou.hotels_booking.model.Apartment;
import com.chuyashkou.hotels_booking.service.ApartmentService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ApartmentFacadeImpl implements ApartmentFacade {

    private final ApartmentService apartmentService;

    public ApartmentFacadeImpl(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    @Override
    public Optional<Apartment> save(Apartment apartment) {
        return apartmentService.save(apartment);
    }

    @Override
    public Optional<Apartment> update(Apartment apartment) {
        return apartmentService.update(apartment);
    }

    @Override
    public boolean deleteById(Long id) {
        return apartmentService.deleteById(id);
    }
}
