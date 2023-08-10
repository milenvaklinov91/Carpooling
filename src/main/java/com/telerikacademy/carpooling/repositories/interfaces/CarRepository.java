package com.telerikacademy.carpooling.repositories.interfaces;

import com.telerikacademy.carpooling.models.Car;

public interface CarRepository {

    void create(Car car);

    void update(Car car);

    void delete(int id);

    Car getCarById(int id);


    }
