package com.chuyashkou.hotels_booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "comforts")
public class Comfort {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean tv;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean conditioner;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean bar;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean refrigerator;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean balcony;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean jacuzzi;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean breakfast;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean wifi;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean transfer;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean parking;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean swimmingPool;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean gym;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean restaurant;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean pets;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean accessibilityFeatures;
}
