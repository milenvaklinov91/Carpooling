package com.telerikacademy.carpooling.services.interfaces;

import com.telerikacademy.carpooling.models.Car;
import com.telerikacademy.carpooling.models.User;

public interface CarService {
    void create(Car car, User user);

    void update(Car car, User user);

    void delete(int id, User user);

    Car getCarById(int id);

    Car notExtraStorage(int id, User user);

    Car noSmoke(int id, User user);

    Car noAirConditioner(int id, User user);

    Car noPetAvailable(int id, User user);

    Car noConsumeFood(int id, User user);

    Car noConsumeDrink(int id, User user);
}
