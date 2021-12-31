package com.uber.software.services;

import com.uber.software.models.AppUser;
import com.uber.software.models.Rating;
import com.uber.software.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class  AppUserService {
    private final AppUserRepository appUserRepository;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

    public String addUser(AppUser user) {
        appUserRepository.save(user);
        return "Added";
    }




}
