package com.telerikacademy.carpooling.controllers.rest;

import com.telerikacademy.carpooling.controllers.AuthenticationHelper;
import com.telerikacademy.carpooling.mappers.CarMapper;
import com.telerikacademy.carpooling.services.interfaces.CarService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
