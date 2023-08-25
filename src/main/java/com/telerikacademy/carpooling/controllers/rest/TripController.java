package com.telerikacademy.carpooling.controllers.rest;

import com.telerikacademy.carpooling.controllers.AuthenticationHelper;
import com.telerikacademy.carpooling.exceptions.AuthorizationException;
import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.exceptions.UnauthorizedOperationException;
import com.telerikacademy.carpooling.mappers.TripMapper;
import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.dtos.TripDto;
import com.telerikacademy.carpooling.models.filterOptions.TripFilterOptions;
import com.telerikacademy.carpooling.services.interfaces.TripService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
@RestController
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
                             @RequestParam(required = false) String username,
                             @RequestParam(required = false) String sortBy,
                             @RequestParam(required = false) String sortOrder) {
        TripFilterOptions tripFilterOptions = new TripFilterOptions(startLocation,
                endLocation,
                departureTime,
                costPerPerson,
                username,
                sortBy,
                sortOrder);
        return tripService.getAll(tripFilterOptions);
    }

    @GetMapping("/{id}")
    public Trip getTravelById(@PathVariable int id) {
        try {
            return tripService.getTripById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/completed")
    public List<Trip> getAllCompletedTrips() {
        return tripService.getAllCompletedTrips();
    }

    @PostMapping
    public Trip create(@RequestHeader HttpHeaders headers, @Valid @RequestBody TripDto tripDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Trip trip = tripMapper.fromTripDto(tripDto);
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
            Trip trip = tripService.getTripById(id);
            trip = tripMapper.fromTripDtoUpdate(trip,tripDto);
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

    @PutMapping("/{id}/in-progress")
    public void inProgressTripStatus(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Trip trip = tripService.getTripById(id);
            tripService.inProgressTripStatus(trip, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PutMapping("/{id}/finished")
    public void finishedTripStatus(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Trip trip = tripService.getTripById(id);
            tripService.finishedTripStatus(trip, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

}
