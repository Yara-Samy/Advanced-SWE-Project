package com.uber.software.models;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;


@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String driver;
    private String customer;
    private Integer rate;

    public Integer getId() {
        return id;
    }

    public String getDriver() {
        return driver;
    }

    public String getCustomer() {
        return customer;
    }

    public Integer getRate() {
        return rate;
    }
}
