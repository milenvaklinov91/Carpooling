package com.telerikacademy.carpooling.repositories.interfaces;

import com.telerikacademy.carpooling.models.TripRequestStatus;
import com.telerikacademy.carpooling.models.TripStatus;

import java.util.List;

public interface TripRequestStatusRepository {
    TripRequestStatus getTripRequestStatusId(int id);
    List<TripRequestStatus> getAll();
}
