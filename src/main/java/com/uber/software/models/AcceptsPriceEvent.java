package com.uber.software.models;

import com.uber.software.enums.EventName;
import com.uber.software.services.EventService;

import java.time.LocalTime;

public class AcceptsPriceEvent extends Event{
    private String Username;

    public AcceptsPriceEvent( Integer rideID, String Username) {
        super(EventName.UserAcceptsPrice, LocalTime.now(), rideID);
        this.Username = Username;
        EventService.updateEvents(this);
    }

    @Override
    public String tostring() {
        return "rideID: "+this.rideID+"\neventName: "+this.eventName+"\neventTime: "+this.eventTime+"\nUsername: "+this.Username+"\n";
    }
}
