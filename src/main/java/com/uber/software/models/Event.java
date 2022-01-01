package com.uber.software.models;

import com.uber.software.enums.EventName;
<<<<<<< HEAD
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
=======

import java.time.LocalTime;
>>>>>>> 0fef4e874b584546c5a0199f17cad8ac441150ec


public abstract class Event {


    protected EventName eventName;
<<<<<<< HEAD
    protected ZonedDateTime eventTime;
    protected Integer rideID;

    public Event(EventName eventName, ZonedDateTime eventTime, Integer rideID) {
=======
    protected LocalTime eventTime;
    protected Integer rideID;

    public Event(EventName eventName, LocalTime eventTime, Integer rideID) {
>>>>>>> 0fef4e874b584546c5a0199f17cad8ac441150ec
        this.eventName = eventName;
        this.eventTime = eventTime;
        this.rideID = rideID;


    }

    public EventName getEventName() {
        return eventName;
    }

<<<<<<< HEAD
    public ZonedDateTime getEventTime() {
=======
    public LocalTime getEventTime() {
>>>>>>> 0fef4e874b584546c5a0199f17cad8ac441150ec
        return eventTime;
    }

    public Integer getRideID() {
        return rideID;
    }
<<<<<<< HEAD
    public void hello(){

    }
=======

    public abstract String tostring();

>>>>>>> 0fef4e874b584546c5a0199f17cad8ac441150ec
}
