package com.uber.software.controllers;

<<<<<<< HEAD
import com.uber.software.models.AppUser;
=======
import com.uber.software.models.*;
>>>>>>> 0fef4e874b584546c5a0199f17cad8ac441150ec
import com.uber.software.services.AppUserService;
import com.uber.software.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
<<<<<<< HEAD
import org.w3c.dom.events.Event;

import java.util.List;
import java.util.Map;
=======

import java.util.List;
>>>>>>> 0fef4e874b584546c5a0199f17cad8ac441150ec

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

<<<<<<< HEAD
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
=======



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
>>>>>>> 0fef4e874b584546c5a0199f17cad8ac441150ec
}
