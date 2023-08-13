package com.telerikacademy.carpooling.services.interfaces;

import com.telerikacademy.carpooling.models.Driver;
import com.telerikacademy.carpooling.models.User;

import java.util.List;

public interface DriverService {
    List<Driver> getAllDriversLicense(User logUser);
    Driver getDriverLicenseByUsername(String username,User logUser);
}
