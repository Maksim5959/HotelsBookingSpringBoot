package com.chuyashkou.hotels_booking.service.impl;

import com.chuyashkou.hotels_booking.model.Hotel;
import com.chuyashkou.hotels_booking.repository.HotelRepository;
import com.chuyashkou.hotels_booking.service.HotelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    public HotelServiceImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public Hotel save(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    public Optional<Hotel> findByLegalName(String legalName) {
        return hotelRepository.findByLegalName(legalName);
    }
}
