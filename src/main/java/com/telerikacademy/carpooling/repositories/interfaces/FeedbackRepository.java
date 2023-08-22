package com.telerikacademy.carpooling.repositories.interfaces;

import com.telerikacademy.carpooling.models.Feedback;
import com.telerikacademy.carpooling.models.FeedbackComment;
import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.filterOptions.FeedbackFilterOptions;

import java.util.List;

public interface FeedbackRepository {

    Feedback getFeedbackById(int id);
//
//    Feedback getFeedbackByRatedUser(int id);

//    List<Feedback> getRatingOfUser(int id);

    void create(Feedback feedback);

    List<User> getTopRatedUsers();
    List<User> getTopRatedPassengers();


    List<Feedback> getAll(FeedbackFilterOptions feedbackFilterOptions);

    List<Feedback> findAllFeedbacksByUser(int userId);
    List<Integer> getRatingValuesForUser(int userId);
    double getAverageRatingValueForUser(int userId);

    void delete(int id);

//    List<User> getTopRatedDrivers();
//    List<User> topPassengers();
}
