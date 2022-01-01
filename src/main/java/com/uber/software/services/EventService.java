package com.uber.software.services;

import com.uber.software.models.Event;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    public static List<Event> eventList = new ArrayList<>();

    public static void updateEvents(Event event){
        eventList.add(event);
    }

    public List<String> rideEvents(Integer ID){
        return eventList.stream().filter(event -> event.getRideID().equals(ID)).collect(Collectors.toList()).stream().map(Event::tostring).collect(Collectors.toList());
    }
}
