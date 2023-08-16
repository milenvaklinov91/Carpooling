package com.telerikacademy.carpooling.controllers.rest;

import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.models.TripRequestStatus;
import com.telerikacademy.carpooling.services.interfaces.TripRequestStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/trip-request-status")
public class TripRequestStatusController {

    private final TripRequestStatusService tripRequestStatusService;


    public TripRequestStatusController(TripRequestStatusService tripRequestStatusService) {
        this.tripRequestStatusService = tripRequestStatusService;

    }

    @GetMapping("/{id}")
    public TripRequestStatus getTripStatusId(@PathVariable int id) {
        try {
            return tripRequestStatusService.getTripRequestStatusId(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping
    public List<TripRequestStatus> getAll() {
        return tripRequestStatusService.getAll();
    }
}

