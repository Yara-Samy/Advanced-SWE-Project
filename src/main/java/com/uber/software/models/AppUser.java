package com.uber.software.models;

import com.uber.software.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.uber.software.repositories.RatingRepository;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

/*{
    "username": "c2",
    "password": "c2",
    "email": "c2",
    "role":0
}*/
public class AppUser {
    @Id
    private String username;
    private String password;
    private String email;
   private LocalDate dateOfBirth;
    private UserRole role;
    private float avgRate;

    public float getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(float avgRate) {
        this.avgRate = avgRate;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public UserRole getRole() {
        return role;
    }
}
