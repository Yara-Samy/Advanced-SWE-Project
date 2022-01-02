package com.uber.software.models;

import com.uber.software.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.uber.software.repositories.RatingRepository;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AppUser {
    @Id
    private String username;
    private String password;
    private String email;
    private LocalDate dateOfBirth;
    private double balance;
    private UserRole role;
    private float avgRate;
    private int seats;
    private boolean isVerified;
    private boolean isSuspended;
}
