package com.telerikacademy.carpooling.repositories.interfaces;

import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.models.filterOptions.TripFilterOptions;

import java.util.List;

public interface TripRepository {
    Trip getTripById(int id);

    List<Trip> getAll(TripFilterOptions tripFilterOptions);

    List<Trip> getAllCompletedTrips();

    public Long countAllCompletedTrips();

    void create(Trip trip);

    void modify(Trip trip);

    void delete(int id);

    List<Trip> findAllTravelsByUser(int userId);
    Long countCompletedTripsByUser(int userId);
}
