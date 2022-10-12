package com.chuyashkou.hotels_booking.repository;

import com.chuyashkou.hotels_booking.model.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
}
