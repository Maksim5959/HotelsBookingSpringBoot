package com.chuyashkou.hotels_booking.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "apartments")
public class Apartment {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Category category;

    @Column(nullable = false)
    private Integer singleBedCount;

    @Column(nullable = false)
    private Integer doubleBedCount;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private Comfort comfort;

    @OneToMany
    @JoinColumn(name = "apartment_id")
    @EqualsAndHashCode.Exclude
    Set<Booking> bookings;
}
