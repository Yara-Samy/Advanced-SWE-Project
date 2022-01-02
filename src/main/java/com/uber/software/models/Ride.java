package com.uber.software.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_username")
    private AppUser customer;
    @ManyToOne
    @JoinColumn(name = "driver_username")
    private AppUser driver;
    private double price;
    private int numberOfPassengers;

    @OneToMany
    @JoinColumn(name = "offers_id")
    private List<Offer> offers;
    private String source;
    private String destination;

    public Ride(AppUser customer, String source, String destination, int numberOfPassengers) {
        this.customer = customer;
        this.source = source;
        this.destination = destination;
        this.numberOfPassengers = numberOfPassengers;
    }

}
