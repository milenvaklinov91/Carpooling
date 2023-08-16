package com.telerikacademy.carpooling.services.interfaces;

import com.telerikacademy.carpooling.models.TripStatus;

import java.util.List;

public interface TripStatusService {
    TripStatus getTripStatusId(int id);
    List<TripStatus> getAll();

}
