package com.chuyashkou.hotels_booking.facade.impl;

import com.chuyashkou.hotels_booking.dto.BookingDto;
import com.chuyashkou.hotels_booking.facade.BookingFacade;
import com.chuyashkou.hotels_booking.model.Booking;
import com.chuyashkou.hotels_booking.service.BookingService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BookingFacadeImpl implements BookingFacade {

    private final BookingService bookingService;
    private final ModelMapper modelMapper;

    public BookingFacadeImpl(BookingService bookingService, ModelMapper modelMapper) {
        this.bookingService = bookingService;
        this.modelMapper = modelMapper;
    }


    @Override
    public Optional<BookingDto> findById(Long id) {
        Optional<Booking> optionalBooking = bookingService.findById(id);
        return optionalBooking.map(b -> modelMapper.map(b, BookingDto.class));
    }

    @Override
    public Optional<BookingDto> save(Booking booking) {
        Optional<Booking> optionalBooking = bookingService.save(booking);
        return optionalBooking.map(b -> modelMapper.map(b, BookingDto.class));
    }

    @Override
    public List<BookingDto> findByHotelId(Long hotelId) {
        return bookingService.findByHotelId(hotelId).stream()
                .map(b -> modelMapper.map(b, BookingDto.class))
                .sorted(Comparator.comparing(BookingDto::getId))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDto> findByUserId(Long userId) {
        return bookingService.findByUserId(userId).stream()
                .map(b -> modelMapper.map(b, BookingDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        bookingService.deleteById(id);
    }

    @Override
    public void confirmBooking(Long id) {
        bookingService.confirmBooking(id);
    }
}
