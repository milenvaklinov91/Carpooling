package com.telerikacademy.carpooling.controllers.rest;

import com.telerikacademy.carpooling.exceptions.DuplicatePasswordException;
import com.telerikacademy.carpooling.exceptions.EntityDuplicateException;
import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.models.Travel;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.dtos.UserDto;
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

    public TravelController(TravelService travelService) {
        this.travelService = travelService;
    }

    @GetMapping
    public List<Travel> getAll(@RequestParam(required = false) String startLocation,
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
    public Travel getTravelById(@PathVariable int id) {
        try {
            return travelService.getTravelById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


    @PostMapping
    public Travel create(@Valid @RequestBody UserDto userDto) {
        /*try {
            User user = userMapper.fromDto(userDto);
            service.create(user);
            return user;
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }*/
        return null;
    }

    @PutMapping("/{id}")
    public String update(@RequestHeader HttpHeaders headers, @PathVariable int id,
                         @Valid @RequestBody UserDto userDto) {
        /*try {
            User logUser = authenticationHelper.tryGetUser(headers);
            User user = userMapper.fromUserDto(id, userDto);
            service.update(user, logUser);
            return "User was successfully updated!";
        } catch (DuplicatePasswordException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }*/
        return null;
    }

    @DeleteMapping("/{id}")
    public String delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        /*User user = authenticationHelper.tryGetUser(headers);
        service.delete(id, user);*/
        return "User was successfully deleted!";
    }

}
