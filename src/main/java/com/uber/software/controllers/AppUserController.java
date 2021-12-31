package com.uber.software.controllers;

import com.uber.software.models.AppUser;
import com.uber.software.services.AppUserService;
import com.uber.software.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class AppUserController {
    private final AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/all")
    public List<AppUser> getAllUsers() {
        return appUserService.getAllUsers();
    }

    @PostMapping("/add")
    @ResponseBody
    public String addUser(@RequestBody AppUser user) {
        return appUserService.addUser(user);
    }
}
