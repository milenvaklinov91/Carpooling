package com.telerikacademy.carpooling.repositories.interfaces;

import com.telerikacademy.carpooling.models.Travel;

import java.util.List;

public interface TravelRepository {
    Travel getTravelById(int id);
    List<Travel> getAll();
    void create(Travel travel);
    void modify(Travel travel);
    void delete(int id);
}
