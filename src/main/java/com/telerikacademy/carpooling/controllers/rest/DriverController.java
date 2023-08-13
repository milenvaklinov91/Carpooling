package com.telerikacademy.carpooling.controllers.rest;

import com.telerikacademy.carpooling.controllers.AuthenticationHelper;
import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.models.Driver;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.services.interfaces.DriverService;
import com.telerikacademy.carpooling.services.interfaces.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {
    private final AuthenticationHelper authenticationHelper;
    private final DriverService driverService;
    private final UserService service;

    public DriverController(AuthenticationHelper authenticationHelper, DriverService driverService, UserService service) {
        this.authenticationHelper = authenticationHelper;
        this.driverService = driverService;
        this.service = service;
    }

    @GetMapping
    public List<Driver> getAllDriversLicense(@RequestHeader HttpHeaders headers) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            service.getUserDetails(user.getId());
            return driverService.getAllDriversLicense(user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    @GetMapping("/username")
    public Driver getDriverLicenseByUsername(@RequestHeader HttpHeaders headers, @RequestHeader String username){
        try {
            User user = authenticationHelper.tryGetUser(headers);
            service.getUserDetails(user.getId());
            return driverService.getDriverLicenseByUsername(username,user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
