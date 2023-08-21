package com.telerikacademy.carpooling.services.interfaces;

import com.telerikacademy.carpooling.models.Feedback;
import com.telerikacademy.carpooling.models.User;

import java.util.List;

public interface FeedbackService {

    Feedback getFeedbackById(int id);

    List<Feedback> getRatingByUser(int userId);

    void create(Feedback feedback, User user);
}
