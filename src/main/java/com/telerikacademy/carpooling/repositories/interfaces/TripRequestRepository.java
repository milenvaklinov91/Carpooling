package com.telerikacademy.carpooling.repositories.interfaces;

import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.models.TripRequest;

import java.util.List;

public interface TripRequestRepository {
    TripRequest getTripRequestById(int id);

    List<TripRequest> getAll();

    void create(TripRequest tripRequest);

    void modify(TripRequest tripRequest);

    void delete(int id);
}
