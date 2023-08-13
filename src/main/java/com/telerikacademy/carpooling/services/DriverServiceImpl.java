package com.telerikacademy.carpooling.services;

import com.telerikacademy.carpooling.exceptions.UnauthorizedOperationException;
import com.telerikacademy.carpooling.models.Driver;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.repositories.DriverRepositoryImpl;
import com.telerikacademy.carpooling.repositories.interfaces.DriverRepository;
import com.telerikacademy.carpooling.services.interfaces.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {
    private DriverRepository driverRepository;
@Autowired
    public DriverServiceImpl(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public List<Driver> getAllDriversLicense(User logUser){
        if (!(logUser.isAdmin())) {
            throw new UnauthorizedOperationException("You're not authorized for this operation");
        }
    return driverRepository.allDrivers();
    }
    public Driver getDriverLicenseByUsername(String username,User logUser){
        if (!(logUser.isAdmin())) {
            throw new UnauthorizedOperationException("You're not authorized for this operation");
        }
        return driverRepository.getDriverByUsername(username);
    }

}
