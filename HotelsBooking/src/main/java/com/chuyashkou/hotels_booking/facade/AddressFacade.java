package com.chuyashkou.hotels_booking.facade;

import com.chuyashkou.hotels_booking.model.Address;

import java.util.Optional;

public interface AddressFacade {

    Optional<Address> findById(Long id);

    Optional<Address> findByUserId(Long userId);

    Optional<Address> findByCountryAndCityAndStreetAndHouseAndBuildingAndApartmentNumber(
            String country, String city, String street, String house, String building, String apartmentNumber);

    void deleteById(Long id);
}
