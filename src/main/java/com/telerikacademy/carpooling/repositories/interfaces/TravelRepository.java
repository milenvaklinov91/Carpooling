package com.telerikacademy.carpooling.repositories.interfaces;

import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.models.filterOptions.TravelFilterOptions;

import java.util.List;

public interface TravelRepository {
    Trip getTravelById(int id);
    List<Trip> getAll(TravelFilterOptions travelFilterOptions);
    void create(Trip trip);
    void modify(Trip trip);
    void delete(int id);
    List<Trip> findAllTravelsByUser(int userId);
}
