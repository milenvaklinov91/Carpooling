package com.telerikacademy.carpooling.mappers;

import com.telerikacademy.carpooling.models.Rating;
import com.telerikacademy.carpooling.models.dtos.RatingDto;
import com.telerikacademy.carpooling.repositories.interfaces.RatingRepository;
import com.telerikacademy.carpooling.services.interfaces.RatingService;
import com.telerikacademy.carpooling.services.interfaces.TripService;
import org.springframework.stereotype.Component;

@Component
public class RatingMapper {

    private final RatingRepository ratingRepository;
    private final RatingService ratingService;

    private final TripService tripService;

    public RatingMapper(RatingRepository ratingRepository, RatingService ratingService, TripService tripService) {
        this.ratingRepository = ratingRepository;
        this.ratingService = ratingService;
        this.tripService = tripService;
    }

    public Rating fromRatingDto(RatingDto ratingDto) {
        Rating rating = new Rating();
        rating.setRatingValue(ratingDto.getRatingValue());
        rating.setComment(ratingDto.getComment());
        rating.setTripId(tripService.getTravelById(ratingDto.getTripId()).getTravelId());
        return rating;
    }
}
