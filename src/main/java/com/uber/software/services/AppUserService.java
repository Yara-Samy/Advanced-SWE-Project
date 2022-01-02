package com.uber.software.services;

import com.uber.software.models.AppUser;
import org.springframework.stereotype.Service;

public interface AppUserService {

    String login(String userName, String password);
    String register(AppUser user);
}
