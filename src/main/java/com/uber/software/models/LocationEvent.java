package com.uber.software.models;

import com.uber.software.enums.EventName;
import com.uber.software.services.EventService;

import java.time.LocalDateTime;
<<<<<<< HEAD
import java.time.ZonedDateTime;
=======
import java.time.LocalTime;
>>>>>>> 0fef4e874b584546c5a0199f17cad8ac441150ec

public class LocationEvent extends Event{
    private String CaptainName;
    private String Username;

<<<<<<< HEAD
    public LocationEvent(ZonedDateTime eventTime, Integer rideID, String captainName, String username) {
        super(EventName.CaptainArrivedToLocation, eventTime, rideID);
        CaptainName = captainName;
        Username = username;
        EventService.notify((org.w3c.dom.events.Event) this);
=======
    public LocationEvent(Integer rideID, String captainName, String username) {
        super(EventName.CaptainArrivedToLocation, LocalTime.now(), rideID);
        CaptainName = captainName;
        Username = username;
        EventService.updateEvents(this);
    }

    @Override
    public String tostring() {
        return "rideID: "+this.rideID+"\n eventName: "+this.eventName+"\n eventTime: "+this.eventTime+"\nCaptainName: "+this.CaptainName+"\n Username: "+this.Username+"\n";
>>>>>>> 0fef4e874b584546c5a0199f17cad8ac441150ec
    }
}
