package com.chuyashkou.hotels_booking.model;

import lombok.Getter;

import java.util.Comparator;

@Getter
public enum HotelsFilter {

    PRICE_UP {
        @Override
        public Comparator<Hotel> getComparator() {
            Comparator<Hotel> comparator = Comparator.comparing(h -> h.getApartments().stream()
                    .mapToDouble(Apartment::getPrice)
                    .min()
                    .orElse(0));
            return comparator.thenComparing(Hotel::getId);
        }
    },
    PRICE_DOWN {
        @Override
        public Comparator<Hotel> getComparator() {
            Comparator<Hotel> comparator = Comparator.comparing(h -> h.getApartments().stream()
                    .mapToDouble(Apartment::getPrice)
                    .min()
                    .orElse(0));
            return comparator.thenComparing(Hotel::getId).reversed();
        }
    },
    NAME_UP {
        @Override
        public Comparator<Hotel> getComparator() {
            return Comparator.comparing(Hotel::getBrand, String.CASE_INSENSITIVE_ORDER).thenComparing(Hotel::getId);
        }
    },
    NAME_DOWN {
        @Override
        public Comparator<Hotel> getComparator() {
            return Comparator.comparing(Hotel::getBrand, String.CASE_INSENSITIVE_ORDER).thenComparing(Hotel::getId).reversed();
        }
    },
    RATING_UP {
        @Override
        public Comparator<Hotel> getComparator() {
            Comparator<Hotel> comparator = Comparator.comparingInt(hotel -> hotel.getStars().getStarsCount());
            return comparator.thenComparing(Hotel::getId);
        }
    },
    RATING_DOWN {
        @Override
        public Comparator<Hotel> getComparator() {
            Comparator<Hotel> comparator = Comparator.comparingInt(hotel -> hotel.getStars().getStarsCount());
            return comparator.thenComparing(Hotel::getId).reversed();
        }
    },
    DEFAULT;

    public Comparator<Hotel> getComparator() {
        return Comparator.comparing(Hotel::getId);
    }
}
