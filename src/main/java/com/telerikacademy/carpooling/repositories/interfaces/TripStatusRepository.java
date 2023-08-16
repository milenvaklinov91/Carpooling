package com.telerikacademy.carpooling.repositories.interfaces;

import com.telerikacademy.carpooling.models.TripStatus;

import java.util.List;

public interface TripStatusRepository {
    TripStatus getTripStatusId(int id);
    List<TripStatus> getAll();
}
