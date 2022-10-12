package com.chuyashkou.hotels_booking.model;

import lombok.Getter;

@Getter
public enum Stars {

    ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5);

    private final int starsCount;

    Stars(int starsCount) {
        this.starsCount = starsCount;
    }
}
