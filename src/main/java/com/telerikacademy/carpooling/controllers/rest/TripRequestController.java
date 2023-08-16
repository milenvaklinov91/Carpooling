package com.telerikacademy.carpooling.controllers.rest;

import com.telerikacademy.carpooling.controllers.AuthenticationHelper;
import com.telerikacademy.carpooling.exceptions.AuthorizationException;
import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.mappers.TripRequestMapper;
import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.models.TripRequest;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.dtos.TripDto;
import com.telerikacademy.carpooling.models.dtos.TripRequestDto;
import com.telerikacademy.carpooling.services.interfaces.TripRequestService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/trip-requests")
public class TripRequestController {
    private final TripRequestService tripRequestService;
    private final AuthenticationHelper authenticationHelper;

    private final TripRequestMapper tripRequestMapper;
    public TripRequestController(TripRequestService tripRequestService,
                                 AuthenticationHelper authenticationHelper,
                                 TripRequestMapper tripRequestMapper) {
        this.tripRequestService = tripRequestService;
        this.authenticationHelper = authenticationHelper;
        this.tripRequestMapper = tripRequestMapper;
    }

    @GetMapping("/{id}")
    public TripRequest getTravelById(@PathVariable int id) {
        try {
            return tripRequestService.getTripRequestById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public TripRequest create(@RequestHeader HttpHeaders headers,
                              @Valid @RequestBody TripRequestDto tripRequestDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            TripRequest tripRequest = tripRequestMapper.fromTripRequestDto(tripRequestDto);
            tripRequestService.create(tripRequest, user);
            return tripRequest;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    /*@PutMapping("/{id}")
    public TripRequest modify(@RequestHeader HttpHeaders headers, @PathVariable int id,
                       @Valid @RequestBody TripRequestDto tripRequestDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            TripRequest tripRequest = tripRequestMapper.fromTripRequestDto(tripRequestDto);
            tripRequestService.modify(tripRequest, user);
            return tripRequest;
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }*/

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        User user = authenticationHelper.tryGetUser(headers);
        tripRequestService.delete(id, user);
    }

}
