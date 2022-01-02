package com.uber.software.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "driver_username")
    private AppUser driver;
    private String source;

    public Area(AppUser driver, String source) {
        this.driver = driver;
        this.source = source;
    }
}
