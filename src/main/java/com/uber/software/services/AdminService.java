package com.uber.software.services;

import com.uber.software.enums.UserRole;
import com.uber.software.models.AppUser;
import com.uber.software.models.Area;
import com.uber.software.models.DiscountArea;
import com.uber.software.repositories.AppUserRepository;
import com.uber.software.repositories.AreaRepository;
import com.uber.software.repositories.DiscountAreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final AppUserRepository appUserRepository;
    //private final AreaRepository areaRepository;
    private final DiscountAreaRepository discountAreaRepository;

    @Autowired
    public AdminService(AppUserRepository appUserRepository,DiscountAreaRepository discountAreaRepository) {
        this.appUserRepository = appUserRepository;
        //this.areaRepository = areaRepository;
        this.discountAreaRepository = discountAreaRepository;
    }

    public List<AppUser> showAllUsers() {
        return appUserRepository.findAll();
    }

    public List<AppUser> listAllPendingDrivers() {
        return appUserRepository.findByRole(UserRole.Driver).stream().filter(appUser -> !appUser.isVerified()).collect(Collectors.toList());
    }

    public String verifyDriver(String userName) {
        Optional<AppUser> user = appUserRepository.findById(userName);
        if (user.isEmpty())
            return "Username does not exist";
        if (user.get().getRole() != UserRole.Driver)
            return "User is not a driver";
        if (user.get().isVerified())
            return String.format("Driver %s is already verified", userName);
        user.get().setVerified(true);
        appUserRepository.save(user.get());
        return String.format("Driver %s verified", userName);
    }

    public String suspendUser(String userName) {
        Optional<AppUser> user = appUserRepository.findById(userName);
        if (user.isEmpty())
            return "Username does not exist";
        if (user.get().isSuspended())
            return "User is already suspended";
        user.get().setSuspended(true);
        appUserRepository.save(user.get());
        return String.format("User %s suspended", userName);
    }

//    public List<Area> showAreas() {
//        return areaRepository.findAll();
//    }

    public String addDiscountArea(String area) {
        if (discountAreaRepository.existsById(area))
            return "Area already has a discount";
        DiscountArea discountArea = new DiscountArea(area);
        discountAreaRepository.save(discountArea);
        return "Added 10% discount to " + area;
    }
}
