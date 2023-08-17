package com.telerikacademy.carpooling.mappers;

import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.models.dtos.TripDto;
import com.telerikacademy.carpooling.models.enums.TripStatus;
import com.telerikacademy.carpooling.services.interfaces.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TripMapper {

    private TripService tripService;
    @Autowired
    public TripMapper(TripService tripService) {
        this.tripService = tripService;
    }

    public Trip fromTravelDto(TripDto tripDto){
        Trip trip = new Trip();
        trip.setStartLocation(tripDto.getStartLocation());
        trip.setEndLocation(tripDto.getEndLocation());
        trip.setDepartureTime(tripDto.getDepartureTime());
        trip.setCostPerPerson(tripDto.getCostPerPerson());
        trip.setAvailableSeats(tripDto.getAvailableSeats());
        trip.setDescription(tripDto.getDescription());
        trip.setTripStatus(TripStatus.AWAITING);
        return trip;
    }

    public Trip fromDto(int id, TripDto tripDto) {
        Trip trip = fromTravelDto(tripDto);
        trip.setTravelId(id);
        Trip creator = tripService.getTripById(id);
        trip.setCreatedBy(creator.getCreatedBy());
        return trip;
    }


}
