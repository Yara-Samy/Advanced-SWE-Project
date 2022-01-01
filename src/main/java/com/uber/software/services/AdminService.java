package com.uber.software.services;

import com.uber.software.enums.UserRole;
import com.uber.software.models.AppUser;
import com.uber.software.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService {
    private final AppUserRepository appUserRepository;

    @Autowired
    public AdminService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public List<AppUser> listAllPendingDrivers() {
        return appUserRepository.findByRole(UserRole.Driver).stream().filter(appUser -> !appUser.isVerified()).collect(Collectors.toList());
    }

    public String verifyDriver(String userName) {
        Optional<AppUser> user = appUserRepository.findById(userName);
        if (user.isPresent()) {
            user.get().setVerified(true);
            appUserRepository.save(user.get());
            return String.format("Driver %s verified", userName);
        }
        return "Username does not exist";
    }

    public String suspendUser(String userName) {
        Optional<AppUser> user = appUserRepository.findById(userName);
        if (user.isPresent()) {
            user.get().setSuspended(true);
            appUserRepository.save(user.get());
            return String.format("User %s suspended", userName);
        }
        return "Username does not exist";
    }
}
