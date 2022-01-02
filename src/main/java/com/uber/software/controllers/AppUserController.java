package com.uber.software.controllers;

import com.uber.software.models.AppUser;
import com.uber.software.services.AppUserService;
import com.uber.software.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.events.Event;

import java.util.List;
import java.util.Map;

public interface AppUserController {
    String register(@RequestBody AppUser user);
    String login(@RequestParam Map<String, String> credentials);
}
