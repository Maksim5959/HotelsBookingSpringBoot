package com.chuyashkou.hotels_booking.dto;

import com.chuyashkou.hotels_booking.model.Category;
import com.chuyashkou.hotels_booking.model.Comfort;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApartmentDto {

    private Long id;

    private String name;

    private Double price;

    private Category category;

    private Integer singleBedCount;

    private Integer doubleBedCount;

    private Comfort comfort;
}
