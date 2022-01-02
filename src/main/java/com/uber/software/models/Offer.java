package com.uber.software.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "ride_id")
    private Ride ride;
    @ManyToOne
    @JoinColumn(name = "driver_username")
    private AppUser driver;
    private double price;

    public Offer(Ride ride, double price, AppUser driver) {
        this.ride = ride;
        this.price = price;
        this.driver = driver;
    }
}
