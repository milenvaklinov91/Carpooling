package com.telerikacademy.carpooling.services.interfaces;

import com.telerikacademy.carpooling.models.Car;
import com.telerikacademy.carpooling.models.Rating;
import com.telerikacademy.carpooling.models.User;

import java.util.List;

public interface RatingService {

    List<Rating> getRatingByUser(int userId);

    void create(Rating rating, User user);
}
