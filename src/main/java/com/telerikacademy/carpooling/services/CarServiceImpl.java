package com.telerikacademy.carpooling.services;

import com.telerikacademy.carpooling.exceptions.EmailExitsException;
import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.exceptions.UnauthorizedOperationException;
import com.telerikacademy.carpooling.models.Car;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.repositories.interfaces.CarRepository;
import com.telerikacademy.carpooling.services.interfaces.CarService;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public void create(Car car, User user) {
        car.setUserCreatedBy(user);
        if (car.getUserCreatedBy().isBlocked()) {
            throw new UnauthorizedOperationException("You`re blocked!");
        }
        if(!(user.isDriver())){
            throw new UnauthorizedOperationException("You are not authorized to perform this operation");
        }
        carRepository.create(car);
    }

    @Override
    public void update(Car car, User user) {
        if (user.isBlocked()) {
            throw new UnauthorizedOperationException("You`re blocked!!!");
        } else if (!(car.getUserCreatedBy().getUsername().equals(user.getUsername()))) {
            throw new UnauthorizedOperationException("You're not authorized to perform this operation");
        }
        carRepository.update(car);
    }

    @Override
    public void delete(int id, User user) {
        Car car = carRepository.getCarById(id);
        if (user.isBlocked()) {
            throw new UnauthorizedOperationException("You`re blocked!");
        } else if (!(user.isAdmin() || car.getUserCreatedBy().getUsername().equals(user.getUsername()))) {
            throw new UnauthorizedOperationException("You're not authorized to perform this operation");
        }
        carRepository.delete(id);
    }

    @Override
    public Car getCarById(int id) {
        return carRepository.getCarById(id);
    }

    public boolean hasExtraStorage(Car car) {
        return car.isExtraStorage();
    }

    public boolean isSmokeAllowed(Car car) {
        return car.isSmoke();
    }

    public boolean hasAirConditioner(Car car) {
        return car.isAirConditioner();
    }

    public boolean isPetAvailable(Car car) {
        return car.isPetAvailable();
    }

    public boolean canConsumeFood(Car car) {
        return car.isConsumeFood();
    }

    public boolean canConsumeDrink(Car car) {
        return car.isConsumeDrink();
    }


}
