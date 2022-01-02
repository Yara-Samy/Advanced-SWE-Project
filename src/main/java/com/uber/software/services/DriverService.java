package com.uber.software.services;

import com.uber.software.enums.UserRole;
import com.uber.software.models.*;
import com.uber.software.repositories.AppUserRepository;
import com.uber.software.repositories.AreaRepository;
import com.uber.software.repositories.OfferRepository;
import com.uber.software.repositories.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DriverService implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final AreaRepository areaRepository;
    private final RideRepository rideRepository;
    private final OfferRepository offerRepository;

    @Autowired
    public DriverService(AppUserRepository appUserRepository, AreaRepository areaRepository, RideRepository rideRepository, OfferRepository offerRepository) {
        this.appUserRepository = appUserRepository;
        this.areaRepository = areaRepository;
        this.rideRepository = rideRepository;
        this.offerRepository = offerRepository;
    }

    @Override
    public String login(String userName, String password) {
        Optional<AppUser> user = appUserRepository.findById(userName);
        return user.map(driver -> driver.getRole() != UserRole.Driver ? "User is not a driver" : !driver.getPassword().equals(password) ? "Wrong password" : driver.isSuspended() ? "Account is suspended" : !driver.isVerified() ? "Account is not verified yet" : String.format("Welcome back, %s!", userName)).orElse("User not found");
    }

    @Override
    public String register(AppUser user) {
        if (appUserRepository.existsById(user.getUsername()))
            return "Username is taken";
        user.setRole(UserRole.Driver);
        user.setSuspended(false);
        user.setVerified(false);
        appUserRepository.save(user);
        return "Registered successfully";
    }

    public String addFavoriteArea(String username, String source) {
        Optional<AppUser> driver = appUserRepository.findById(username);
        if (driver.isEmpty())
            return "User not found";
        if (driver.get().getRole() != UserRole.Driver)
            return "User is not a driver";
        Area area = new Area(driver.get(), source);
        areaRepository.save(area);
        return String.format("Area %s added to %s", source, username);
    }

    public List<Ride> showAvailableRides(String username) {
        Optional<AppUser> user = appUserRepository.findById(username);
        if (user.isEmpty())
            return null;
        List<Area> areas = areaRepository.findByDriver(user.get());
        List<Ride> rides = rideRepository.findAll();
        List<Ride> availableRides = new ArrayList<>();
        for (Ride ride : rides) {
            for (Area area : areas)
                if (ride.getSource().equalsIgnoreCase(area.getSource()) && user.get().getSeats() >= ride.getNumberOfPassengers() && ride.getDriver() == null)
                    availableRides.add(ride);
        }
        return availableRides;
    }

    public String addOffer(String username, Integer id, double price) {
        Optional<Ride> ride = rideRepository.findById(id);
        if (ride.isEmpty())
            return "No such ride available";
        List<Ride> rides = showAvailableRides(username);
        if (rides == null)
            return "User does not exist";
        for (Ride ride1 : rides)
            if (Objects.equals(ride1.getId(), id)) {
                Offer offer = new Offer(ride1, price, appUserRepository.getById(username));
                ride1.getOffers().add(offer);
                rideRepository.save(ride1);
                offerRepository.save(offer);
                Event event = new PriceToRideEvent(ride1.getId(), username, price);
                return "Offer added";
            }
        return "Ride is not suitable";
    }

    public String startRide(String username, Integer id) {
        Optional<AppUser> user = appUserRepository.findById(username);
        if (user.isEmpty())
            return "User does not exist";
        Optional<Ride> ride = rideRepository.findById(id);
        if (ride.isEmpty())
            return "Ride does not exist";
        if (!ride.get().getDriver().getUsername().equals(username))
            return String.format("%s is not the captain of this ride", username);
        Event event = new LocationEvent(id, username, ride.get().getCustomer().getUsername());
        return "Ride started";
    }

    public String endRide(String username, Integer id) {
        Optional<AppUser> user = appUserRepository.findById(username);
        if (user.isEmpty())
            return "User does not exist";
        Optional<Ride> ride = rideRepository.findById(id);
        if (ride.isEmpty())
            return "Ride does not exist";
        if (!ride.get().getDriver().getUsername().equals(username))
            return String.format("%s is not the captain of this ride", username);
        Event event = new DestinationEvent(id, username, ride.get().getCustomer().getUsername());
        return "Ride started";
    }
}
