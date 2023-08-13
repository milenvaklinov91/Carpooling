package com.telerikacademy.carpooling.repositories.interfaces;

import com.telerikacademy.carpooling.models.Driver;

import java.util.List;

public interface DriverRepository {
    List<Driver> allDrivers();
    Driver getDriverByUsername(String username);
}
