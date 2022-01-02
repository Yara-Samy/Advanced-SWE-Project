package com.uber.software.controllers;

import com.uber.software.models.AppUser;
import com.uber.software.models.Holidays;
import com.uber.software.models.Rating;
import com.uber.software.models.Ride;
import com.uber.software.services.DriverService;
import com.uber.software.services.RatingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/driver")
public class DriverController implements AppUserController {

    private final DriverService driverService;
    private final RatingService ratingService;

    public DriverController(DriverService driverService, RatingService ratingService) {
        this.driverService = driverService;
        this.ratingService = ratingService;
    }

    @Override
    @PostMapping("/register")
    @ResponseBody
    public String register(AppUser user) {
        return driverService.register(user);
    }

    @Override
    @GetMapping("/login")
    @ResponseBody
    public String login(Map<String, String> credentials) {
        String userName = credentials.get("userName");
        String password = credentials.get("password");
        return driverService.login(userName, password);
    }

    @PostMapping("/{username}/{source}")
    @ResponseBody
    public String addFavoriteArea(@PathVariable String username, @PathVariable String source) {
        return driverService.addFavoriteArea(username, source);
    }

    @GetMapping("/{username}/ratings")
    @ResponseBody
    public List<Rating> getAllRatings(@PathVariable String username) {
        return ratingService.listDriverRatings(username);
    }

    @GetMapping("/{username}/availableRides")
    @ResponseBody
    public List<Ride> showAvailableRides(@PathVariable String username) {
        return driverService.showAvailableRides(username);
    }

    @PostMapping("addOffer/{username}")
    @ResponseBody
    public String addOffer(@PathVariable String username, @RequestParam Integer id, @RequestParam double price) {
        return driverService.addOffer(username, id, price);
    }

    @GetMapping("{username}/start/{id}")
    @ResponseBody
    public String startRide(@PathVariable String username, @PathVariable Integer id) {
        return driverService.startRide(username, id);
    }

    @GetMapping("{username}/end/{id}")
    @ResponseBody
    public String endRide(@PathVariable String username, @PathVariable Integer id) {
        return driverService.endRide(username, id);
    }
}
