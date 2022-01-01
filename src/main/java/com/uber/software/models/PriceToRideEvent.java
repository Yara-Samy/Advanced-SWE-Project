package com.uber.software.models;

import com.uber.software.enums.EventName;
import com.uber.software.services.EventService;

<<<<<<< HEAD
import java.time.ZonedDateTime;
=======
import java.time.LocalDateTime;
import java.time.LocalTime;
>>>>>>> 0fef4e874b584546c5a0199f17cad8ac441150ec

public class PriceToRideEvent extends Event{
   private String CaptainName;
   private double price;

<<<<<<< HEAD
   public PriceToRideEvent(ZonedDateTime eventTime, Integer rideID, String captainName, double price) {
      super(EventName.PriceToRide, eventTime, rideID);
      CaptainName = captainName;
      this.price = price;
      EventService.notify((org.w3c.dom.events.Event) this);
=======

   public PriceToRideEvent( Integer rideID, String captainName, double price) {
      super(EventName.PriceToRide, LocalTime.now(), rideID);
      CaptainName = captainName;
      this.price = price;
      EventService.updateEvents(this);
   }

   @Override
   public String tostring() {
      return "rideID: "+this.rideID+"\n eventName: "+this.eventName+"\n eventTime: "+this.eventTime+"\nCaptainName: "+this.CaptainName+"\n price: "+this.price+"\n";
>>>>>>> 0fef4e874b584546c5a0199f17cad8ac441150ec
   }
}
