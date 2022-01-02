package com.uber.software.controllers;

import com.uber.software.models.AppUser;
import com.uber.software.models.Area;
import com.uber.software.services.AdminService;
import com.uber.software.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    private final EventService eventService;

    @Autowired
    public AdminController(AdminService adminService, EventService eventService) {
        this.adminService = adminService;
        this.eventService = eventService;
    }

    @GetMapping("/showAllUsers")
    @ResponseBody
    public List<AppUser> showAllUsers() {
        return adminService.showAllUsers();
    }

    @GetMapping("/pendingDrivers")
    @ResponseBody
    public List<AppUser> listAllPendingDrivers() {
        return adminService.listAllPendingDrivers();
    }

    @PostMapping("/verify/{userName}")
    @ResponseBody
    public String verifyDriver(@PathVariable String userName) {
        return adminService.verifyDriver(userName);
    }

    @PostMapping("/suspend/{userName}")
    @ResponseBody
    public String suspendUser(@PathVariable String userName) {
        return adminService.suspendUser(userName);
    }

   // @GetMapping("/areas")
   // @ResponseBody
    //public List<Area> showAreas() {
     //   return adminService.showAreas();
   // }

    @GetMapping("/events/{id}")
    @ResponseBody
    public List<String> showRideEvents(@PathVariable Integer id) {
        return eventService.rideEvents(id);
    }

    @PostMapping("/addDiscountArea")
    @ResponseBody
    public String addDiscountArea(@RequestParam String username) {
        return adminService.addDiscountArea(username);
    }


//    //test function for events but will not be needed in real implementation
//    @PostMapping("/accept/{id}/{username}")
//    @ResponseBody
//    public String addEvent(@PathVariable Integer id, @PathVariable String username) {
//        new AcceptsPriceEvent(id, username);
//        return "new accept event";
//    }
//
//    @PostMapping("/des")
//    @ResponseBody
//    public String desevent(@RequestBody DestinationEvent event) {
//
//        return "new des event";
//    }
}
