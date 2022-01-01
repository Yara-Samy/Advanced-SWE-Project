package com.uber.software.models;

import com.uber.software.enums.EventName;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;


public abstract class Event {


    protected EventName eventName;
    protected ZonedDateTime eventTime;
    protected Integer rideID;

    public Event(EventName eventName, ZonedDateTime eventTime, Integer rideID) {
        this.eventName = eventName;
        this.eventTime = eventTime;
        this.rideID = rideID;


    }

    public EventName getEventName() {
        return eventName;
    }

    public ZonedDateTime getEventTime() {
        return eventTime;
    }

    public Integer getRideID() {
        return rideID;
    }
    public void hello(){

    }
}
