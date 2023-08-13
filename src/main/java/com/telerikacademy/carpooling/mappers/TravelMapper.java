package com.telerikacademy.carpooling.mappers;

import com.telerikacademy.carpooling.models.Travel;
import com.telerikacademy.carpooling.models.dtos.TravelDto;
import com.telerikacademy.carpooling.services.interfaces.TravelService;

public class TravelMapper {

    private TravelService travelService;

    public TravelMapper(TravelService travelService) {
        this.travelService = travelService;
    }

    public Travel fromTravelDto(TravelDto travelDto){
        Travel travel = new Travel();
        travel.setStartLocation(travelDto.getStartLocation());
        travel.setEndLocation(travelDto.getEndLocation());
        travel.setDepartureTime(travelDto.getDepartureTime());
        travel.setCostPerPerson(travelDto.getCostPerPerson());
        travel.setAvailableSeats(travelDto.getAvailableSeats());
        travel.setDescription(travelDto.getDescription());
        return travel;
    }

    public Travel fromDto(int id, TravelDto travelDto) {
        Travel travel = fromTravelDto(travelDto);
        travel.setTravelId(id);
        Travel creator = travelService.getTravelById(id);
        travel.setCreatedBy(creator.getCreatedBy());
        return travel;
    }


}
