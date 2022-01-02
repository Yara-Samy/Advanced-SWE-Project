package com.uber.software.models;

import com.uber.software.enums.EventName;
import com.uber.software.services.EventService;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class PriceToRideEvent extends Event{
   private String CaptainName;
   private double price;


   public PriceToRideEvent( Integer rideID, String captainName, double price) {
      super(EventName.PriceToRide, LocalTime.now(), rideID);
      CaptainName = captainName;
      this.price = price;
      EventService.updateEvents(this);
   }

   @Override
   public String tostring() {
      return "rideID: "+this.rideID+" eventName: "+this.eventName+" eventTime: "+this.eventTime+" CaptainName: "+this.CaptainName+" price: "+this.price;
   }
}