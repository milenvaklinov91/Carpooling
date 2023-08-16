package com.telerikacademy.carpooling.services.interfaces;

import com.telerikacademy.carpooling.models.Car;
import com.telerikacademy.carpooling.models.User;

public interface CarService {
    void create(Car car, User user);

    void update(Car car, User user);

    void delete(int id, User user);

    Car getCarById(int id);

    Car extraStorage(int id, User user);

    Car notExtraStorage(int id, User user);

    boolean hasAirConditioner(Car car);

    boolean isPetAvailable(Car car);

    boolean canConsumeFood(Car car);

    boolean canConsumeDrink(Car car);
}
