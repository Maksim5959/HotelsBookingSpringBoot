package com.chuyashkou.hotels_booking.facade.impl;

import com.chuyashkou.hotels_booking.dto.ApartmentDto;
import com.chuyashkou.hotels_booking.dto.HotelDto;
import com.chuyashkou.hotels_booking.facade.HotelFacade;
import com.chuyashkou.hotels_booking.model.Apartment;
import com.chuyashkou.hotels_booking.model.Hotel;
import com.chuyashkou.hotels_booking.model.HotelsSearchData;
import com.chuyashkou.hotels_booking.service.HotelService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class HotelFacadeImpl implements HotelFacade {

    private final HotelService hotelService;
    private final ModelMapper modelMapper;

    public HotelFacadeImpl(HotelService hotelService, ModelMapper modelMapper) {
        this.hotelService = hotelService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<HotelDto> save(Hotel hotel) {
        Optional<Hotel> optionalHotel = hotelService.save(hotel);
        return optionalHotel.map(h -> modelMapper.map(h, HotelDto.class));
    }

    @Override
    public Optional<HotelDto> update(Hotel hotel) {
        Optional<Hotel> optionalHotel = hotelService.update(hotel);
        return optionalHotel.map(h -> modelMapper.map(h, HotelDto.class));
    }

    @Override
    public Optional<HotelDto> findByUserId(Long userId) {
        Optional<Hotel> optionalHotel = hotelService.findByUserId(userId);
        Optional<HotelDto> optionalHotelDto = optionalHotel.map(h -> modelMapper.map(h, HotelDto.class));
        optionalHotelDto.ifPresent(hotelDto -> hotelDto.setApartments(hotelDto.getApartments().stream()
                .sorted(Comparator.comparing(ApartmentDto::getName))
                .collect(Collectors.toCollection(LinkedHashSet::new))));
        return optionalHotelDto;
    }

    @Override
    public Optional<HotelDto> findById(Long id) {
        Optional<Hotel> optionalHotel = hotelService.findById(id);
        return optionalHotel.map(h -> modelMapper.map(h, HotelDto.class));
    }

    @Override
    public Optional<HotelDto> delete(Long id) {
        Optional<Hotel> optionalHotel = hotelService.delete(id);
        return optionalHotel.map(h -> modelMapper.map(h, HotelDto.class));
    }

    @Override
    public List<HotelDto> findAll() {
        return hotelService.findAll().stream()
                .map(h -> modelMapper.map(h, HotelDto.class))
                .sorted(Comparator.comparing(HotelDto::getId))
                .collect(Collectors.toList());
    }

    @Override
    public List<HotelDto> findByHotelSearchData(HotelsSearchData searchData) {
        return hotelService.findByHotelSearchData(searchData).stream()
                .map(h -> modelMapper.map(h, HotelDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void updateRegistrationField(Long id, boolean registered) {
        hotelService.updateRegistrationField(id, registered);
    }

    @Override
    public void addNewApartment(Apartment apartment) {
        hotelService.addNewApartment(apartment);
    }
}
