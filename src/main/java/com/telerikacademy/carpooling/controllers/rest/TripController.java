package com.telerikacademy.carpooling.controllers.rest;

import com.telerikacademy.carpooling.controllers.AuthenticationHelper;
import com.telerikacademy.carpooling.exceptions.AuthorizationException;
import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.mappers.TripMapper;
import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.dtos.TripDto;
import com.telerikacademy.carpooling.models.filterOptions.TravelFilterOptions;
import com.telerikacademy.carpooling.services.interfaces.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
@Controller
@RequestMapping("/api/trips")
public class TripController {
    private final TripService tripService;
    private final AuthenticationHelper authenticationHelper;
    private final TripMapper tripMapper;
    public TripController(TripService tripService, AuthenticationHelper authenticationHelper, TripMapper tripMapper) {
        this.tripService = tripService;
        this.authenticationHelper = authenticationHelper;
        this.tripMapper = tripMapper;
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
        return tripService.getAll(travelFilterOptions);
    }

    @GetMapping("/{id}")
    public Trip getTravelById(@PathVariable int id) {
        try {
            return tripService.getTravelById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public Trip create(@RequestHeader HttpHeaders headers, @Valid @RequestBody TripDto tripDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Trip trip = tripMapper.fromTravelDto(tripDto);
            tripService.create(trip, user);
            return trip;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Trip modify(@RequestHeader HttpHeaders headers, @PathVariable int id,
                       @Valid @RequestBody TripDto tripDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Trip trip = tripMapper.fromDto(id, tripDto);
            tripService.modify(trip, user);
            return trip;
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        User user = authenticationHelper.tryGetUser(headers);
        tripService.delete(id, user);
    }

}
