package com.uber.software.services;

import com.uber.software.enums.UserRole;
import com.uber.software.models.AppUser;
import com.uber.software.models.Rating;
import com.uber.software.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uber.software.repositories.AppUserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {
    private final RatingRepository ratingRepository;
   private final AppUserRepository appUserRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository, AppUserRepository appUserRepository) {
        this.ratingRepository = ratingRepository;
        this.appUserRepository = appUserRepository;
    }

    public String rate(Rating rating){
        if(appUserRepository.existsById(rating.getDriver())&& appUserRepository.existsById(rating.getCustomer())){
            ratingRepository.save(rating);
            float avg = 0;
            for (Rating rating1 : ratingRepository.findByDriver(rating.getDriver()))avg+=rating1.getRate();

            avg/=ratingRepository.findByDriver(rating.getDriver()).size();
            Optional<AppUser> user = appUserRepository.findById(rating.getDriver());
            user.get().setAvgRate(avg);
            appUserRepository.save(user.get());


            return"rated successfully";
        }
        return "failed due to non existing customer or driver";
    }
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    public List<Rating> listDriverRatings(String username){
        if(appUserRepository.existsById(username)){
            if(appUserRepository.findById(username).get().getRole()== UserRole.Driver)return ratingRepository.findByDriver(username);

        }
        return null;
    }


}
