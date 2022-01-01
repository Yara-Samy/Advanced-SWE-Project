package com.uber.software.models;

import com.uber.software.enums.EventName;
import com.uber.software.services.EventService;

import java.time.ZonedDateTime;

public class PriceToRideEvent extends Event{
   private String CaptainName;
   private double price;

   public PriceToRideEvent(ZonedDateTime eventTime, Integer rideID, String captainName, double price) {
      super(EventName.PriceToRide, eventTime, rideID);
      CaptainName = captainName;
      this.price = price;
      EventService.notify((org.w3c.dom.events.Event) this);
   }
}
