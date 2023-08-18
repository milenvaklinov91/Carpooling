package com.telerikacademy.carpooling.mappers;

import com.telerikacademy.carpooling.models.TripRequest;
import com.telerikacademy.carpooling.models.dtos.TripRequestDto;
import com.telerikacademy.carpooling.models.enums.TripRequestStatus;
import com.telerikacademy.carpooling.services.interfaces.TripRequestService;
import com.telerikacademy.carpooling.services.interfaces.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TripRequestMapper {

    private TripRequestService tripRequestService;
    private TripService tripService;
    @Autowired
    public TripRequestMapper(TripRequestService tripRequestService, TripService tripService) {
        this.tripRequestService = tripRequestService;
        this.tripService = tripService;
    }

    public TripRequest fromTripRequestDto(TripRequestDto tripRequestDto) {
        TripRequest tripRequest = new TripRequest();
        tripRequest.setTrip(tripService.getTripById(tripRequestDto.getTripId()));
        tripRequest.setTripRequestStatus(TripRequestStatus.PENDING);
        return tripRequest;
    }
}
