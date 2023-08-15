package com.telerikacademy.carpooling.services;

import com.telerikacademy.carpooling.models.Rating;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.repositories.interfaces.RatingRepository;
import com.telerikacademy.carpooling.services.interfaces.RatingService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }


    @Override
    public List<Rating> getRatingByUser(int userId) {
        return ratingRepository.getRatingByUser(userId);
    }

    @Override
    public void create(Rating rating, User user) {
        rating.setUserByCreatedBy(user);
        ratingRepository.create(rating);
    }
}
