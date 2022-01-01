package com.uber.software.models;

import com.uber.software.enums.EventName;
import com.uber.software.services.EventService;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class LocationEvent extends Event{
    private String CaptainName;
    private String Username;

    public LocationEvent(Integer rideID, String captainName, String username) {
        super(EventName.CaptainArrivedToLocation, LocalTime.now(), rideID);
        CaptainName = captainName;
        Username = username;
        EventService.updateEvents(this);
    }

    @Override
    public String tostring() {
        return "rideID: "+this.rideID+"\n eventName: "+this.eventName+"\n eventTime: "+this.eventTime+"\nCaptainName: "+this.CaptainName+"\n Username: "+this.Username+"\n";
    }
}
