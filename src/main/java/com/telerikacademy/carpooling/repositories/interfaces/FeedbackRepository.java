package com.telerikacademy.carpooling.repositories.interfaces;

import com.telerikacademy.carpooling.models.Feedback;

import java.util.List;

public interface FeedbackRepository {

    List<Feedback> getRatingByUser(int userId);

    void create(Feedback feedback);


}
