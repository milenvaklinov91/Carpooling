package com.telerikacademy.carpooling.services.interfaces;

import com.telerikacademy.carpooling.models.Travel;
import com.telerikacademy.carpooling.models.User;

import java.util.List;

public interface TravelService {
    Travel getTravelById(int id);

    List<Travel> getAll();

    void create(Travel travel, User user);

    void modify(Travel travel, User user);

    void delete(int id,User user);
}
