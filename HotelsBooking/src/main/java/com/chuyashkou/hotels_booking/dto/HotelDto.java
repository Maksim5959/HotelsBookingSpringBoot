package com.chuyashkou.hotels_booking.dto;

import com.chuyashkou.hotels_booking.model.Address;
import com.chuyashkou.hotels_booking.model.Stars;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelDto {

    private Long id;

    private String legalName;

    private String brand;

    private Stars stars;

    private boolean isRegistered;

    private Double minPrice;

    private Address address;

    private Set<ApartmentDto> apartments;
}
