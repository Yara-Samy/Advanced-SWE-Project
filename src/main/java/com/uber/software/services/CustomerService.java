package com.uber.software.services;

import com.uber.software.enums.UserRole;
import com.uber.software.models.*;
import com.uber.software.repositories.AppUserRepository;
import com.uber.software.repositories.DiscountAreaRepository;
import com.uber.software.repositories.OfferRepository;
import com.uber.software.repositories.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
public class CustomerService implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final RideRepository rideRepository;
    private final OfferRepository offerRepository;
    private final DiscountAreaRepository discountAreaRepository;
    private final RatingService ratingService;

    @Autowired
    public CustomerService(AppUserRepository appUserRepository, RideRepository rideRepository, OfferRepository offerRepository, DiscountAreaRepository discountAreaRepository, RatingService ratingService) {
        this.appUserRepository = appUserRepository;
        this.rideRepository = rideRepository;
        this.offerRepository = offerRepository;
        this.discountAreaRepository = discountAreaRepository;
        this.ratingService = ratingService;
    }

    @Override
    public String login(String userName, String password) {
        Optional<AppUser> user = appUserRepository.findById(userName);
        return user.map(appUser -> appUser.getRole() != UserRole.Customer ? "User is not a customer" : !appUser.getPassword().equals(password) ? "Wrong password" : appUser.isSuspended() ? "Account is suspended" : String.format("Welcome back, %s!", userName)).orElse("User not found");
    }

    @Override
    public String register(AppUser user) {
        if (!appUserRepository.existsById(user.getUsername())) {
            user.setRole(UserRole.Customer);
            user.setSuspended(false);
            appUserRepository.save(user);
            return "Registered successfully";
        }
        return "Username is taken";
    }

    public String requestRide(String customer, String source, String destination, int numberOfPassengers) {
        Optional<AppUser> user = appUserRepository.findById(customer);
        if (user.isEmpty())
            return String.format("User %s does not exist", customer);
        Ride ride = new Ride(user.get(), source, destination, numberOfPassengers);
        rideRepository.save(ride);
        return "Ride requested successfully";
    }

    public List<Offer> showOffers(String username) {
        Optional<AppUser> user = appUserRepository.findById(username);
        if (user.isEmpty() || user.get().getRole() != UserRole.Customer)
            return null;
        List<Offer> offers = offerRepository.findAll();
        List<Offer> myOffers = new ArrayList<>();
        for (Offer offer : offers) {
            if (offer.getRide().getCustomer().getUsername().equals(username))
                myOffers.add(offer);
        }
        return myOffers;
    }

    public String acceptOffer(String username, Integer id) {
        Optional<AppUser> user = appUserRepository.findById(username);
        if (user.isEmpty())
            return "User does not exist";
        List<Offer> offers = showOffers(username);
        if (offers == null)
            return "Invalid user";
        for (Offer offer : offers) {
            if (Objects.equals(offer.getId(), id)) {
                offer.getRide().setPrice(offer.getPrice());
                offer.getRide().setDriver(offer.getDriver());
                applyDiscounts(offer.getRide());
                Event event = new AcceptsPriceEvent(id, username);
                offerRepository.save(offer);
                rideRepository.save(offer.getRide());
                return "Offer accepted";
            }
        }
        return "Invalid offer id";
    }

    public String rateDriver(Rating rating) {
        return ratingService.rate(rating);
    }

    private void applyDiscounts(Ride ride) {
        if (ride.getNumberOfPassengers()==2)
        {
            ride.setPrice(ride.getPrice()-(ride.getPrice()*0.1));
        }
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date()).substring(0,5);
        for (Holidays holidays : Holidays.values()) {
            if (holidays.getDate().equals(date))
            {
                ride.setPrice(ride.getPrice() - (ride.getPrice() * 0.05));
            }
        }
        if(discountAreaRepository.existsById(ride.getDestination()))
        {
            ride.setPrice(ride.getPrice()-(ride.getPrice()*0.1));
        }
        if(ride.getCustomer().getDateOfBirth().getMonth()== LocalDate.now().getMonth()
                &&ride.getCustomer().getDateOfBirth().getDayOfMonth()== LocalDate.now().getDayOfMonth())
        {
            ride.setPrice(ride.getPrice()-(ride.getPrice()*0.1));
        }
        if (rideRepository.findByCustomer(ride.getCustomer()).isEmpty()) {
            ride.setPrice(ride.getPrice() - (ride.getPrice() * 0.05));
        }
    }
}
