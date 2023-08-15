package com.telerikacademy.carpooling.repositories.interfaces;

import com.telerikacademy.carpooling.models.Car;
import com.telerikacademy.carpooling.models.Rating;

import java.util.List;

public interface RatingRepository {

    List<Rating> getRatingByUser(int userId);

    void create(Rating rating);


}
