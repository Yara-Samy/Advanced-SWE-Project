package com.uber.software.controllers;

import com.uber.software.models.AppUser;
import com.uber.software.models.Rating;
import com.uber.software.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rate")
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping("/all")
    public List<Rating> getAllRatings() {
        return ratingService.getAllRatings();
    }

    @PostMapping()
    @ResponseBody
    public String rateDriver(@RequestBody Rating rating) {
         return ratingService.rate(rating);
    }

    @GetMapping("/{username}")
    public List<Rating> listDriverRatings(@PathVariable String username){
        return ratingService.listDriverRatings(username);
    }
}
