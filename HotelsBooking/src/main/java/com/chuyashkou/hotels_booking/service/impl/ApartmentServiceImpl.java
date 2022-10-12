package com.chuyashkou.hotels_booking.service.impl;

import com.chuyashkou.hotels_booking.model.Apartment;
import com.chuyashkou.hotels_booking.repository.ApartmentRepository;
import com.chuyashkou.hotels_booking.service.ApartmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional
public class ApartmentServiceImpl implements ApartmentService {

    private final ApartmentRepository apartmentRepository;

    public ApartmentServiceImpl(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    @Override
    public Optional<Apartment> save(Apartment apartment) {
        return Optional.of(apartmentRepository.save(apartment));
    }

    @Override
    public Optional<Apartment> update(Apartment apartment) {
        Optional<Apartment> optionalApartment = apartmentRepository.findById(apartment.getId());
        AtomicReference<Optional<Apartment>> atomicReference = new AtomicReference<>(Optional.empty());
        optionalApartment.ifPresent(a -> {
            if (a.getBookings().size() == 0) {
                atomicReference.set(Optional.of(apartmentRepository.save(apartment)));
            }
        });
        return atomicReference.get();
    }

    @Override
    public boolean deleteById(Long id) {
        Optional<Apartment> optionalApartment = apartmentRepository.findById(id);
        AtomicReference<Boolean> atomicReference = new AtomicReference<>(false);
        optionalApartment.ifPresent(apartment -> {
            if (apartment.getBookings().size() == 0) {
                apartmentRepository.deleteById(id);
                atomicReference.set(true);
            }
        });
        return atomicReference.get();
    }
}
