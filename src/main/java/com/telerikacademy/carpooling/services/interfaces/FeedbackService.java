package com.telerikacademy.carpooling.services.interfaces;

import com.telerikacademy.carpooling.models.Feedback;
import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.models.TripRequest;
import com.telerikacademy.carpooling.models.User;

import java.util.List;

public interface FeedbackService {

    Feedback getFeedbackById(int id);

    List<Feedback> getRatingByUser(int userId);

    void createFeedbackForDriver(Feedback feedback, Trip trip, User user);

    void createFeedbackForPassenger(Feedback feedback, TripRequest tripRequest, User user);

}
