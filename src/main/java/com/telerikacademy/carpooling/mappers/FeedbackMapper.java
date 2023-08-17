package com.telerikacademy.carpooling.mappers;

import com.telerikacademy.carpooling.models.Feedback;
import com.telerikacademy.carpooling.models.dtos.FeedbackDto;

import com.telerikacademy.carpooling.services.interfaces.TripService;
import org.springframework.stereotype.Component;

@Component
public class FeedbackMapper {

    private final TripService tripService;

    public FeedbackMapper(TripService tripService) {

        this.tripService = tripService;
    }

    public Feedback fromFeedbackDto(FeedbackDto feedbackDto) {
        Feedback feedback = new Feedback();
        feedback.setRatingValue(feedbackDto.getRatingValue());
        feedback.setTripId(tripService.getTripById(feedbackDto.getTripId()).getTravelId());
        return feedback;
    }
}
