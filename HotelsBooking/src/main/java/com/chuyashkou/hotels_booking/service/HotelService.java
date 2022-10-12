package com.chuyashkou.hotels_booking.service;

import com.chuyashkou.hotels_booking.model.Apartment;
import com.chuyashkou.hotels_booking.model.Hotel;
import com.chuyashkou.hotels_booking.model.HotelsSearchData;

import java.util.List;
import java.util.Optional;

public interface HotelService {

    Optional<Hotel> save(Hotel hotel);

    Optional<Hotel> update(Hotel hotel);

    Optional<Hotel> findByUserId(Long userId);

    Optional<Hotel> findById(Long id);

    Optional<Hotel> delete(Long id);

    List<Hotel> findAll();

    List<Hotel> findByHotelSearchData(HotelsSearchData searchData);

    void updateRegistrationField(Long id, boolean registered);

    void addNewApartment(Apartment apartment);
}
