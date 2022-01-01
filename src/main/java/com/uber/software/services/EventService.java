package com.uber.software.services;

<<<<<<< HEAD
import com.uber.software.models.PriceToRideEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.events.Event;

import java.util.List;
=======
import com.uber.software.models.Event;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
>>>>>>> 0fef4e874b584546c5a0199f17cad8ac441150ec

@Service
public class EventService {

<<<<<<< HEAD
    static List<Event> eventList;
    public static void notify(Event event){
        eventList.add(event);
    }

    /*public List<Event> rideEvents(Integer ID){
        return eventList.stream().filter(event -> event.);
    }*/
=======
    public static List<Event> eventList = new ArrayList<>();

    public static void updateEvents(Event event){
        eventList.add(event);
    }

    public List<String> rideEvents(Integer ID){
        return eventList.stream().filter(event -> event.getRideID().equals(ID)).collect(Collectors.toList()).stream().map(Event::tostring).collect(Collectors.toList());
    }
>>>>>>> 0fef4e874b584546c5a0199f17cad8ac441150ec
}
