package com.telerikacademy.carpooling.mappers;

import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.models.dtos.TripDto;
import com.telerikacademy.carpooling.models.enums.TripStatus;
import com.telerikacademy.carpooling.services.interfaces.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TripMapper {

    private final TripService tripService;
    private final Map map;

    @Autowired
    public TripMapper(TripService tripService, Map map) {
        this.tripService = tripService;
        this.map = map;
    }

    public Trip fromTripDto(TripDto tripDto) {
        Trip trip = new Trip();
        trip.setStartLocation(tripDto.getStartLocation());
        trip.setEndLocation(tripDto.getEndLocation());
        trip.setDepartureTime(tripDto.getDepartureTime());
        trip.setCostPerPerson(tripDto.getCostPerPerson());
        trip.setAvailableSeats(tripDto.getAvailableSeats());
        trip.setDescription(tripDto.getDescription());
        trip.setTripStatus(TripStatus.AWAITING);

        /*trip.setDistance(map.getDistance(tripDto.getStartLocation(),tripDto.getEndLocation()));*/

        return trip;
    }


    public Trip fromDto(int id, TripDto tripDto) {
        Trip trip = fromTripDto(tripDto);
        trip.setTripId(id);
        Trip creator = tripService.getTripById(id);
        trip.setCreatedBy(creator.getCreatedBy());
        return trip;
    }

    public Trip fromTripDtoUpdate(Trip trip,TripDto tripDto){
        trip.setStartLocation(tripDto.getStartLocation());
        trip.setEndLocation(tripDto.getEndLocation());
        trip.setDepartureTime(tripDto.getDepartureTime());
        trip.setCostPerPerson(tripDto.getCostPerPerson());
        trip.setAvailableSeats(tripDto.getAvailableSeats());
        trip.setDescription(tripDto.getDescription());
        return trip;
    }


}
