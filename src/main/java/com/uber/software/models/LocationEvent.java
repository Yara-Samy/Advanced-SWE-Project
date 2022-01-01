package com.uber.software.models;

import com.uber.software.enums.EventName;
import com.uber.software.services.EventService;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class LocationEvent extends Event{
    private String CaptainName;
    private String Username;

    public LocationEvent(ZonedDateTime eventTime, Integer rideID, String captainName, String username) {
        super(EventName.CaptainArrivedToLocation, eventTime, rideID);
        CaptainName = captainName;
        Username = username;
        EventService.notify((org.w3c.dom.events.Event) this);
    }
}
