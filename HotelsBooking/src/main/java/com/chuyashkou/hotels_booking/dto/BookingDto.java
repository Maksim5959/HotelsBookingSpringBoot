package com.chuyashkou.hotels_booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {

    private Long id;

    private LocalDate dateIn;

    private LocalDate dateOut;

    private LocalDateTime addTime;

    private LocalDateTime confirmTime;

    private Double totalPrice;

    private boolean isConfirm;

    private ApartmentDto apartment;

    private UserDto user;
}
