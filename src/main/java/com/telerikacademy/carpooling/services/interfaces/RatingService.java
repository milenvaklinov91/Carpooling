package com.telerikacademy.carpooling.services.interfaces;

import com.telerikacademy.carpooling.models.Rating;

import java.util.List;

public interface RatingService {

    List<Rating> getRatingByUser(int userId);

}
