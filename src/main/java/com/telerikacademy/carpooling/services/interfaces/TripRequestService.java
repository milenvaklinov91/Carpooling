package com.telerikacademy.carpooling.services.interfaces;

import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.models.TripRequest;
import com.telerikacademy.carpooling.models.User;


public interface TripRequestService {

    TripRequest getTripRequestById(int id);

    void create(TripRequest tripRequest, Trip trip, User user);

    void modify(TripRequest tripRequest, Trip trip, User user);

    void delete(int id, User user);

    void approveTripRequest(TripRequest tripRequest);

    void rejectTripRequest(TripRequest tripRequest);
}
