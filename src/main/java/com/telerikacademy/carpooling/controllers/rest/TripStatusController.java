package com.telerikacademy.carpooling.controllers.rest;

import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.models.TripStatus;
import com.telerikacademy.carpooling.services.interfaces.TripStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/trip-status")
public class TripStatusController {
    private final TripStatusService tripStatusService;

    public TripStatusController(TripStatusService tripStatusService) {
        this.tripStatusService = tripStatusService;
    }

    @GetMapping("/{id}")
    public TripStatus getTripStatusId(@PathVariable int id) {
        try {
            return tripStatusService.getTripStatusId(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping
    public List<TripStatus> getAll() {
        return tripStatusService.getAll();
    }
}
