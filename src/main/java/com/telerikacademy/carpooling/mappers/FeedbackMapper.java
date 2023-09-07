package com.telerikacademy.carpooling.mappers;

import com.telerikacademy.carpooling.models.Feedback;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.dtos.FeedbackDto;

import com.telerikacademy.carpooling.services.interfaces.TripService;
import com.telerikacademy.carpooling.services.interfaces.UserService;
import org.springframework.stereotype.Component;

@Component
public class FeedbackMapper {

    private final TripService tripService;
    private final UserService userService;

    public FeedbackMapper(TripService tripService, UserService userService) {

        this.tripService = tripService;
        this.userService = userService;
    }

    public Feedback fromFeedbackDto(FeedbackDto feedbackDto) {
        Feedback feedback = new Feedback();
        feedback.setRatingValue(feedbackDto.getRatingValue());
        feedback.setTripId(tripService.getTripById(feedbackDto.getTripId()).getTripId());
        return feedback;
    }

    public Feedback fromFeedbackDtoWithId(FeedbackDto feedbackDto, int id) {
        Feedback feedback = new Feedback();
        feedback.setRatingValue(feedbackDto.getRatingValue());
        feedback.setTripId(feedbackDto.getTripId());
        feedback.setRatedUser(userService.getById(id));
        return feedback;
    }

}
