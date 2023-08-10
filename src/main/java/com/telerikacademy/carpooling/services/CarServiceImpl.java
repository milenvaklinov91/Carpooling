package com.telerikacademy.carpooling.services;

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
        carRepository.create(car);
    }

    @Override
    public void update(Car car, User user) {

    }

    @Override
    public void delete(int id) {
        Car car = carRepository.getCarById(id);
        carRepository.delete(id);

    }
}
