package com.chuyashkou.hotels_booking.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private LocalDate dateIn;

    @Column(nullable = false)
    private LocalDate dateOut;

    private LocalDateTime addTime;

    private LocalDateTime confirmTime;

    @Column(nullable = false)
    private Double totalPrice;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean isConfirm;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Apartment apartment;

    @ManyToOne
    private User user;
}
