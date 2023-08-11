package com.telerikacademy.carpooling.controllers.rest;

import com.telerikacademy.carpooling.controllers.AuthenticationHelper;
import com.telerikacademy.carpooling.services.interfaces.CarService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;
    private final AuthenticationHelper authenticationHelper;

    public CarController(CarService carService, AuthenticationHelper authenticationHelper) {
        this.carService = carService;
        this.authenticationHelper = authenticationHelper;
    }


}
