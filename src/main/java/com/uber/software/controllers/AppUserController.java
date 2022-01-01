package com.uber.software.controllers;

import com.uber.software.models.AppUser;
import com.uber.software.services.AppUserService;
import com.uber.software.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.events.Event;

import java.util.List;
import java.util.Map;

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

    @PostMapping("/register")
    @ResponseBody
    public String register(@RequestBody AppUser user) {
        return appUserService.register(user);
    }

    @GetMapping("/login")
    @ResponseBody
    public String login(@RequestParam Map<String, String> credentials) {
        String userName = credentials.get("userName");
        String password = credentials.get("password");
        return appUserService.login(userName, password);
    }

   /* @GetMapping("/events/{id}")//admin method
    public List<Event> showRideEvents(@PathVariable Integer id){
        return eventService.rideEvents(id);
    }*/
}
