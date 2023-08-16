package com.telerikacademy.carpooling.services.interfaces;

import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.filterOptions.TravelFilterOptions;

import java.util.List;

public interface TripService {
    Trip getTripById(int id);

    List<Trip> getAll(TravelFilterOptions travelFilterOptions);

    void create(Trip trip, User user);

    void modify(Trip trip, User user);

    void delete(int id,User user);
}
