package com.uber.software.models;

import com.uber.software.enums.EventName;
import com.uber.software.services.EventService;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class AcceptsPriceEvent extends Event{
    private String UserName;

    public AcceptsPriceEvent(ZonedDateTime eventTime, Integer rideID, String userName) {
        super(EventName.UserAcceptsPrice, eventTime, rideID);
        UserName = userName;
        EventService.notify((org.w3c.dom.events.Event) this);
    }
}
