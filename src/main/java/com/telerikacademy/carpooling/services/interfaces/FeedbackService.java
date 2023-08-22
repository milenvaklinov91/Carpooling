package com.telerikacademy.carpooling.services.interfaces;

import com.telerikacademy.carpooling.models.Feedback;
import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.models.TripRequest;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.filterOptions.FeedbackFilterOptions;

import java.util.List;

public interface FeedbackService {

    Feedback getFeedbackById(int id);
//    List<Feedback> getRatingOfUser(int userId);
    List<Feedback> findAllFeedbacksByUser(int userId);

    double getAverageRatingValueForUser(int userId);

    List<User> getTopRatedUsers();

    List<User> getTopRatedPassengers();

    List<Integer> getRatingValuesForUser(int userId);
    List<Feedback> getAll(FeedbackFilterOptions feedbackFilterOptions);
    void delete(int id,User user);

    void createFeedbackForDriver(Feedback feedback, Trip trip, User user);

    void createFeedbackForPassenger(Feedback feedback, TripRequest tripRequest, User user);

}
