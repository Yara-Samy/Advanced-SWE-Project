package com.uber.software.services;

import com.uber.software.models.AppUser;
import com.uber.software.models.Rating;
import com.uber.software.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
<<<<<<< HEAD
import java.util.Optional;
=======
>>>>>>> 0fef4e874b584546c5a0199f17cad8ac441150ec

@Service
public class  AppUserService {
    private final AppUserRepository appUserRepository;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

<<<<<<< HEAD
    public String login(String userName, String password) {
        Optional<AppUser> user = appUserRepository.findById(userName);
        return user.map(appUser -> !appUser.getPassword().equals(password) ? "Wrong password" : appUser.isSuspended() ? "Account is suspended" : "Logged in successfully").orElse("User not found");
    }

    public String register(AppUser user) {
        if (!appUserRepository.existsById(user.getUsername())) {
            appUserRepository.save(user);
            return "Registered successfully";
        }
        return "Username is taken";
=======
    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

    public String addUser(AppUser user) {
        appUserRepository.save(user);
        return "Added";
>>>>>>> 0fef4e874b584546c5a0199f17cad8ac441150ec
    }




}
