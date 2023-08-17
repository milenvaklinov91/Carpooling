package com.telerikacademy.carpooling.mappers;

import com.telerikacademy.carpooling.models.Feedback;
import com.telerikacademy.carpooling.models.dtos.RatingDto;
import com.telerikacademy.carpooling.repositories.interfaces.FeedbackRepository;
import com.telerikacademy.carpooling.services.interfaces.FeedbackService;
import com.telerikacademy.carpooling.services.interfaces.TripService;
import org.springframework.stereotype.Component;

@Component
public class FeedbackMapper {

    private final FeedbackRepository feedbackRepository;
    private final FeedbackService feedbackService;

    private final TripService tripService;

    public FeedbackMapper(FeedbackRepository feedbackRepository, FeedbackService feedbackService, TripService tripService) {
        this.feedbackRepository = feedbackRepository;
        this.feedbackService = feedbackService;
        this.tripService = tripService;
    }

    public Feedback fromRatingDto(RatingDto ratingDto) {
        Feedback feedback = new Feedback();
        feedback.setRatingValue(ratingDto.getRatingValue());
        feedback.setTripId(tripService.getTripById(ratingDto.getTripId()).getTravelId());
        return feedback;
    }
}
