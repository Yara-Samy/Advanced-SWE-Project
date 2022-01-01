package com.uber.software.controllers;

import com.uber.software.models.*;
import com.uber.software.services.AppUserService;
import com.uber.software.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /*
    //test function for events but will not be needed in real implementation
    @PostMapping ("/accept/{id}/{username}")
    @ResponseBody
    public String addEvent(@PathVariable Integer id,@PathVariable String username) {
        new AcceptsPriceEvent(id,username);
        return "new accept event";
    }
    @PostMapping("/des")
    public String desevent(@RequestBody DestinationEvent event) {

        return "new des event";
    }*/



    @GetMapping("/events/{id}")//admin method
    public List<String> showRideEvents(@PathVariable Integer id){
        return eventService.rideEvents(id);
    }
}
