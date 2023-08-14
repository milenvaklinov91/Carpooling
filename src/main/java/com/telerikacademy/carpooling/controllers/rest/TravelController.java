package com.telerikacademy.carpooling.controllers.rest;

import com.telerikacademy.carpooling.controllers.AuthenticationHelper;
import com.telerikacademy.carpooling.exceptions.AuthorizationException;
import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.mappers.TravelMapper;
import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.dtos.TravelDto;
import com.telerikacademy.carpooling.models.filterOptions.TravelFilterOptions;
import com.telerikacademy.carpooling.services.interfaces.TravelService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

public class TravelController {
    private final TravelService travelService;
    private final AuthenticationHelper authenticationHelper;
    private final TravelMapper travelMapper;

    public TravelController(TravelService travelService, AuthenticationHelper authenticationHelper, TravelMapper travelMapper) {
        this.travelService = travelService;
        this.authenticationHelper = authenticationHelper;
        this.travelMapper = travelMapper;
    }

    @GetMapping
    public List<Trip> getAll(@RequestParam(required = false) String startLocation,
                             @RequestParam(required = false) String endLocation,
                             @RequestParam(required = false) String departureTime,
                             @RequestParam(required = false) String costPerPerson,
                             @RequestParam(required = false) String sortBy,
                             @RequestParam(required = false) String sortOrder) {
        TravelFilterOptions travelFilterOptions = new TravelFilterOptions(startLocation,
                endLocation,
                departureTime,
                costPerPerson,
                sortBy,
                sortOrder);
        return travelService.getAll(travelFilterOptions);
    }

    @GetMapping("/{id}")
    public Trip getTravelById(@PathVariable int id) {
        try {
            return travelService.getTravelById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public Trip create(@RequestHeader HttpHeaders headers, @Valid @RequestBody TravelDto travelDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Trip trip = travelMapper.fromTravelDto(travelDto);
            travelService.create(trip, user);
            return trip;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Trip modify(@RequestHeader HttpHeaders headers, @PathVariable int id,
                       @Valid @RequestBody TravelDto travelDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Trip trip = travelMapper.fromDto(id, travelDto);
            travelService.modify(trip, user);
            return trip;
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        User user = authenticationHelper.tryGetUser(headers);
        travelService.delete(id, user);
    }

}
