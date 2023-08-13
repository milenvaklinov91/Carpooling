package com.telerikacademy.carpooling.controllers.rest;

import com.telerikacademy.carpooling.controllers.AuthenticationHelper;
import com.telerikacademy.carpooling.exceptions.AuthorizationException;
import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.mappers.CarMapper;
import com.telerikacademy.carpooling.models.Car;
import com.telerikacademy.carpooling.models.Travel;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.dtos.CarDto;
import com.telerikacademy.carpooling.models.dtos.TravelDto;
import com.telerikacademy.carpooling.services.interfaces.CarService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;
    private final AuthenticationHelper authenticationHelper;
    private final CarMapper carMapper;

    public CarController(CarService carService, AuthenticationHelper authenticationHelper, CarMapper carMapper) {
        this.carService = carService;
        this.authenticationHelper = authenticationHelper;
        this.carMapper = carMapper;
    }
    @PostMapping
    public Car create(@RequestHeader HttpHeaders headers, @Valid @RequestBody CarDto carDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Car car = carMapper.fromCarDto(carDto);
            carService.create(car, user);
            return car;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public Car getCarById(@PathVariable int id) {
        try {
            return carService.getCarById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Car update(@RequestHeader HttpHeaders headers, @PathVariable int id,
                         @Valid @RequestBody CarDto carDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Car car = carMapper.fromCarDtoWithId(id, carDto);
            carService.update(car, user);
            return car;
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        User user = authenticationHelper.tryGetUser(headers);
        carService.delete(id, user);
    }

}
