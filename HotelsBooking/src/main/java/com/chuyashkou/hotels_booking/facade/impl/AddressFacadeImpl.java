package com.chuyashkou.hotels_booking.facade.impl;

import com.chuyashkou.hotels_booking.facade.AddressFacade;
import com.chuyashkou.hotels_booking.model.Address;
import com.chuyashkou.hotels_booking.service.AddressService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AddressFacadeImpl implements AddressFacade {

    private final AddressService addressService;

    public AddressFacadeImpl(AddressService addressService) {
        this.addressService = addressService;
    }

    @Override
    public Optional<Address> findById(Long id) {
        return addressService.findById(id);
    }

    @Override
    public Optional<Address> findByUserId(Long userId) {
        return addressService.findByUserId(userId);
    }

    @Override
    public Optional<Address> findByCountryAndCityAndStreetAndHouseAndBuildingAndApartmentNumber
            (String country, String city, String street, String house, String building, String apartmentNumber) {
        return addressService.findByCountryAndCityAndStreetAndHouseAndBuildingAndApartmentNumber
                (country, city, street, house, building, apartmentNumber);
    }

    @Override
    public void deleteById(Long id) {
        addressService.deleteById(id);
    }
}
