package com.chuyashkou.hotels_booking.service.impl;

import com.chuyashkou.hotels_booking.model.Booking;
import com.chuyashkou.hotels_booking.model.Hotel;
import com.chuyashkou.hotels_booking.repository.BookingRepository;
import com.chuyashkou.hotels_booking.service.BookingService;
import com.chuyashkou.hotels_booking.service.HotelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final HotelService hotelService;

    public BookingServiceImpl(BookingRepository bookingRepository, HotelService hotelService) {
        this.bookingRepository = bookingRepository;
        this.hotelService = hotelService;
    }

    @Override
    public Optional<Booking> findById(Long id) {
        return bookingRepository.findById(id);
    }

    @Override
    public Optional<Booking> save(Booking booking) {
        return Optional.of(bookingRepository.save(booking));
    }

    @Override
    public List<Booking> findByHotelId(Long hotelId) {
        Optional<Hotel> optionalHotel = hotelService.findById(hotelId);
        return optionalHotel.map(hotel -> hotel.getApartments().stream()
                .flatMap(apartment -> apartment.getBookings().stream())
                .collect(Collectors.toList())).orElseGet(ArrayList::new);
    }

    @Override
    public List<Booking> findByUserId(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    @Override
    public void deleteById(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public void confirmBooking(Long id) {
        Optional<Booking> optionalBooking = bookingRepository.findById(id);
        optionalBooking.ifPresent(booking -> {
            booking.setConfirm(true);
            bookingRepository.save(booking);
        });
    }
}
