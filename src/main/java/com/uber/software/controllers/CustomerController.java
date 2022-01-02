package com.uber.software.controllers;

import com.uber.software.models.AppUser;
import com.uber.software.models.Offer;
import com.uber.software.models.Rating;
import com.uber.software.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerController implements AppUserController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @PostMapping("/register")
    @ResponseBody
    @Override
    public String register(AppUser user) {
        return customerService.register(user);
    }

    @GetMapping("/login")
    @ResponseBody
    @Override
    public String login(@RequestParam Map<String, String> credentials) {
        String userName = credentials.get("userName");
        String password = credentials.get("password");
        return customerService.login(userName, password);
    }

    @PostMapping("/requestRide")
    @ResponseBody
    public String requestRide(@RequestParam String customer, @RequestParam String source, @RequestParam String destination, @RequestParam Integer numberOfPassengers) {
        return customerService.requestRide(customer, source, destination, numberOfPassengers);
    }

    @GetMapping("/{username}/offers")
    @ResponseBody
    public List<Offer> showOffers(@PathVariable String username) {
        return customerService.showOffers(username);
    }

    @PostMapping("/{username}/acceptOffer/{id}")
    @ResponseBody
    public String acceptOffer(@PathVariable String username, @PathVariable Integer id) {
        return customerService.acceptOffer(username, id);
    }

    @PostMapping("/rate")
    @ResponseBody
    public String rateDriver(@RequestBody Rating rating) {
        return customerService.rateDriver(rating);
    }
}
