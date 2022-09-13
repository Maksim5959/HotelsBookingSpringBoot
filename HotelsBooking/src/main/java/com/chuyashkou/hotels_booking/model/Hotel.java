package com.chuyashkou.hotels_booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "hotels")
public class Hotel {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String legalName;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Stars stars;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean isRegistered;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private Address address;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "hotel_id")
    private Set<Apartment> apartments;
}
