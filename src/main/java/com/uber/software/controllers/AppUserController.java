package com.uber.software.controllers;

import com.uber.software.models.AppUser;
import com.uber.software.services.AppUserService;
import com.uber.software.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.events.Event;

import java.util.List;

@RestController
@RequestMapping("/users")
public class AppUserController {
    private final AppUserService appUserService;
    private final EventService eventService;

    @Autowired
    public AppUserController(AppUserService appUserService, EventService eventService) {
        this.appUserService = appUserService;
        this.eventService = eventService;
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

   /* @GetMapping("/events/{id}")//admin method
    public List<Event> showRideEvents(@PathVariable Integer id){
        return eventService.rideEvents(id);
    }*/
}
