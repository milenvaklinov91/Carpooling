package com.telerikacademy.carpooling.repositories.interfaces;

import com.telerikacademy.carpooling.models.Feedback;
import com.telerikacademy.carpooling.models.FeedbackComment;

import java.util.List;

public interface FeedbackRepository {

    Feedback getFeedbackById(int id);

    List<Feedback> getRatingByUser(int userId);

    void create(Feedback feedback);


}
