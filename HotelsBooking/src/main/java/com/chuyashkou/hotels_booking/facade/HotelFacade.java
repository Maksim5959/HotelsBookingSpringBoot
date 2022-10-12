package com.chuyashkou.hotels_booking.facade;

import com.chuyashkou.hotels_booking.dto.HotelDto;
import com.chuyashkou.hotels_booking.model.Apartment;
import com.chuyashkou.hotels_booking.model.Hotel;
import com.chuyashkou.hotels_booking.model.HotelsSearchData;

import java.util.List;
import java.util.Optional;

public interface HotelFacade {

    Optional<HotelDto> save(Hotel hotel);

    Optional<HotelDto> update(Hotel hotel);

    Optional<HotelDto> findByUserId(Long userId);

    Optional<HotelDto> findById(Long id);

    Optional<HotelDto> delete(Long id);

    List<HotelDto> findAll();

    List<HotelDto> findByHotelSearchData(HotelsSearchData searchData);

    void updateRegistrationField(Long id, boolean registered);

    void addNewApartment(Apartment apartment);
}
