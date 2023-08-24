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

    Car smoke(int id, User user);

    Car notSmoke(int id, User user);

    Car airConditioner(int id, User user);

    Car notAirConditioner(int id, User user);

    Car petAvailable(int id, User user);

    Car notPetAvailable(int id, User user);

    Car consumeFood(int id, User user);

    Car notConsumeFood(int id, User user);

    Car consumeDrink(int id, User user);

    Car notConsumeDrink(int id, User user);
}
