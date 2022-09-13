package com.chuyashkou.hotels_booking.service;

import com.chuyashkou.hotels_booking.model.Address;

import java.util.Optional;

public interface AddressService {

    void deleteById(Long id);

    Optional<Address> findByCountryAndCityAndStreetAndHouseAndBuildingAndApartmentNumber(
            String country, String city, String street, String house, String building, String apartmentNumber);
}
