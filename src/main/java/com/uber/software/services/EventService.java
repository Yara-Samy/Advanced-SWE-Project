package com.uber.software.services;

import com.uber.software.models.PriceToRideEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.events.Event;

import java.util.List;

@Service
public class EventService {

    static List<Event> eventList;
    public static void notify(Event event){
        eventList.add(event);
    }

    /*public List<Event> rideEvents(Integer ID){
        return eventList.stream().filter(event -> event.);
    }*/
}
