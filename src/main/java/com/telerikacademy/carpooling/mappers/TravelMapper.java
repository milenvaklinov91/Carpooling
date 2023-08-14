package com.telerikacademy.carpooling.mappers;

import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.models.dtos.TravelDto;
import com.telerikacademy.carpooling.services.interfaces.TravelService;

public class TravelMapper {

    private TravelService travelService;

    public TravelMapper(TravelService travelService) {
        this.travelService = travelService;
    }

    public Trip fromTravelDto(TravelDto travelDto){
        Trip trip = new Trip();
        trip.setStartLocation(travelDto.getStartLocation());
        trip.setEndLocation(travelDto.getEndLocation());
        trip.setDepartureTime(travelDto.getDepartureTime());
        trip.setCostPerPerson(travelDto.getCostPerPerson());
        trip.setAvailableSeats(travelDto.getAvailableSeats());
        trip.setDescription(travelDto.getDescription());
        return trip;
    }

    public Trip fromDto(int id, TravelDto travelDto) {
        Trip trip = fromTravelDto(travelDto);
        trip.setTravelId(id);
        Trip creator = travelService.getTravelById(id);
        trip.setCreatedBy(creator.getCreatedBy());
        return trip;
    }


}
