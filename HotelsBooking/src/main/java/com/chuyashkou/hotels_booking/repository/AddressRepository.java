package com.chuyashkou.hotels_booking.repository;

import com.chuyashkou.hotels_booking.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByCountryAndCityAndStreetAndHouseAndBuildingAndApartmentNumber(
            String country, String city, String street, String house, String building, String apartmentNumber);

}
