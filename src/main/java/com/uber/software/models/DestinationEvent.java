package com.uber.software.models;

import com.uber.software.enums.EventName;
import com.uber.software.services.EventService;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class DestinationEvent extends Event{
    private String CaptainName;
    private String Username;

    public DestinationEvent(ZonedDateTime eventTime, Integer rideID, String captainName, String username) {
        super(EventName.CaptainArrivedToDestination, eventTime, rideID);
        CaptainName = captainName;
        Username = username;
        EventService.notify((org.w3c.dom.events.Event) this);
    }
}
