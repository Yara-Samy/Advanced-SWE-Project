package com.uber.software.models;

import com.uber.software.enums.EventName;
import com.uber.software.services.EventService;

import java.time.LocalTime;


public abstract class Event {


    protected EventName eventName;
    protected LocalTime eventTime;
    protected Integer rideID;

    public Event(EventName eventName, LocalTime eventTime, Integer rideID) {
        this.eventName = eventName;
        this.eventTime = eventTime;
        this.rideID = rideID;


    }


    public EventName getEventName() {
        return eventName;
    }

    public LocalTime getEventTime() {
        return eventTime;
    }

    public Integer getRideID() {
        return rideID;
    }

    public abstract String tostring();

}