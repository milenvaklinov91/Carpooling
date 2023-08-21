package com.telerikacademy.carpooling.services.interfaces;

import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.filterOptions.TripFilterOptions;

import java.util.List;

public interface TripService {
    Trip getTripById(int id);

    List<Trip> getAll(TripFilterOptions tripFilterOptions);

    void create(Trip trip, User user);

    void modify(Trip trip, User user);

    void delete(int id,User user);
    void inProgressTripStatus(Trip trip, User user);
    void finishedTripStatus(Trip trip, User user);
}
