package com.chuyashkou.hotels_booking.service.impl;

import com.chuyashkou.hotels_booking.model.Booking;
import com.chuyashkou.hotels_booking.repository.BookingRepository;
import com.chuyashkou.hotels_booking.service.BookingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }
}
