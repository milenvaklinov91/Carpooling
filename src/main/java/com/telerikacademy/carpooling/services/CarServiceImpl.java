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

    public Car extraStorage(int id, User user) {
        Car car = carRepository.getCarById(id);
        if (user.getUsername().equals(car.getUserCreatedBy().getUsername()) ) {
            car.setExtraStorage(true);
            carRepository.update(car);
            return car;
        }
        throw new UnauthorizedOperationException("You're not authorized for this operation!");
    }

    public Car notExtraStorage(int id, User user) {
        Car car = carRepository.getCarById(id);
        if (user.getUsername().equals(car.getUserCreatedBy().getUsername()) ) {
            car.setExtraStorage(false);
            carRepository.update(car);
            return car;
        }
        throw new UnauthorizedOperationException("You're not authorized for this operation!");
    }

    public Car noSmoke(int id, User user) {
        Car car = carRepository.getCarById(id);
        if (user.getUsername().equals(car.getUserCreatedBy().getUsername())) {
            car.setSmoke(false);
            carRepository.update(car);
            return car;
        }
        throw new UnauthorizedOperationException("You're not authorized for this operation!");
    }

    public Car noAirConditioner(int id, User user) {
        Car car = carRepository.getCarById(id);
        if (user.getUsername().equals(car.getUserCreatedBy().getUsername())) {
            car.setAirConditioner(false);
            carRepository.update(car);
            return car;
        }
        throw new UnauthorizedOperationException("You're not authorized for this operation!");
    }

    public Car noPetAvailable(int id, User user) {
        Car car = carRepository.getCarById(id);
        if (user.getUsername().equals(car.getUserCreatedBy().getUsername())) {
            car.setPetAvailable(false);
            carRepository.update(car);
            return car;
        }
        throw new UnauthorizedOperationException("You're not authorized for this operation!");
    }

    public Car noConsumeFood(int id, User user) {
        Car car = carRepository.getCarById(id);
        if (user.getUsername().equals(car.getUserCreatedBy().getUsername())) {
            car.setConsumeFood(false);
            carRepository.update(car);
            return car;
        }
        throw new UnauthorizedOperationException("You're not authorized for this operation!");
    }

    public Car noConsumeDrink(int id, User user) {
        Car car = carRepository.getCarById(id);
        if (user.getUsername().equals(car.getUserCreatedBy().getUsername())) {
            car.setConsumeDrink(false);
            carRepository.update(car);
            return car;
        }
        throw new UnauthorizedOperationException("You're not authorized for this operation!");
    }
}