package com.uber.software.controllers;

import com.uber.software.models.AppUser;
import com.uber.software.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/PendingDrivers")
    @ResponseBody
    public List<AppUser> listAllPendingDrivers() {
        return adminService.listAllPendingDrivers();
    }

    @PostMapping("/verify/{userName}")
    public String verifyDriver(@PathVariable("userName") String userName) {
        return adminService.verifyDriver(userName);
    }

    @PostMapping("/suspend/{userName}")
    @ResponseBody
    public String suspendUser(@PathVariable("userName") String userName) {
        return adminService.suspendUser(userName);
    }
}
