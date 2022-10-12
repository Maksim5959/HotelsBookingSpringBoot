package com.chuyashkou.hotels_booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HotelsSearchData {

    private String city;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateIn;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOut;

    private int adultsCount;

    private int childrenCount;

    private int roomsCount;

    private HotelsFilter hotelsFilter;
}
