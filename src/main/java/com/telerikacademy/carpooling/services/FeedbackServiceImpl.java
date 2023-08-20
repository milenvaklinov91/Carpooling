package com.telerikacademy.carpooling.services;

import com.telerikacademy.carpooling.exceptions.UnauthorizedOperationException;
import com.telerikacademy.carpooling.models.Feedback;
import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.repositories.interfaces.FeedbackRepository;
import com.telerikacademy.carpooling.services.interfaces.FeedbackService;
import org.springframework.stereotype.Service;
import com.telerikacademy.carpooling.models.TripRequest;


import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public Feedback getFeedbackById(int id) {
        return feedbackRepository.getFeedbackById(id);
    }

    @Override
    public List<Feedback> getRatingByUser(int userId) {
        return feedbackRepository.getRatingByUser(userId);
    }

    @Override
    public void createFeedbackForDriver(Feedback feedback, Trip trip, User votingUser) {
        User ratedUser = trip.getCreatedBy();
        if (votingUser.equals(ratedUser)) {
            throw new UnauthorizedOperationException("You're not authorized to perform this operation!");
        } else {
            feedback.setUserByCreatedBy(votingUser);
            feedback.setRatedUser(ratedUser);
            feedbackRepository.create(feedback);
        }
    }

    public void createFeedbackForPassenger(Feedback feedback, TripRequest tripRequest, User passenger) {
        Trip trip = tripRequest.getTrip();
        User driver = trip.getCreatedBy();

        if (passenger.equals(driver)) {
            throw new UnauthorizedOperationException("You're not authorized to perform this operation!");
        } else {
            feedback.setUserByCreatedBy(passenger);
            feedback.setRatedUser(driver);
            feedbackRepository.create(feedback);
        }
    }
}


// todo feedback for driver and feedback for passenger