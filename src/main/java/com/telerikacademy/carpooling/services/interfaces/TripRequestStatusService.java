package com.telerikacademy.carpooling.services.interfaces;

import com.telerikacademy.carpooling.models.TripRequestStatus;

import java.util.List;

public interface TripRequestStatusService {

    TripRequestStatus getTripRequestStatusId(int id);
    List<TripRequestStatus> getAll();

}
