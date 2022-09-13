package com.chuyashkou.hotels_booking.model;

public enum Category {

    STANDARD("Standard room"),
    DOUBLE("Double bad room"),
    TRIPLE("Triple bad room"),
    LUX("Lux apartment"),
    PRESIDENT_LUX("President lux apartment");

    private final String name;

    Category(String name) {
        this.name = name;
    }
}
