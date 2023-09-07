package com.telerikacademy.carpooling.services;

import com.telerikacademy.carpooling.exceptions.UnauthorizedOperationException;
import com.telerikacademy.carpooling.models.Feedback;
import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.enums.TripStatus;
import com.telerikacademy.carpooling.models.filterOptions.FeedbackFilterOptions;
import com.telerikacademy.carpooling.repositories.interfaces.FeedbackRepository;
import com.telerikacademy.carpooling.services.interfaces.FeedbackService;
import com.telerikacademy.carpooling.services.interfaces.TripRequestService;
import com.telerikacademy.carpooling.services.interfaces.TripService;
import com.telerikacademy.carpooling.services.interfaces.UserService;
import org.springframework.stereotype.Service;
import com.telerikacademy.carpooling.models.TripRequest;


import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserService userService;
    private final TripService tripService;
    private final TripRequestService tripRequestService;


    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, UserService userService, TripService tripService, TripRequestService tripRequestService) {
        this.feedbackRepository = feedbackRepository;
        this.userService = userService;
        this.tripService = tripService;
        this.tripRequestService = tripRequestService;
    }

    @Override
    public Feedback getFeedbackById(int id) {
        return feedbackRepository.getFeedbackById(id);
    }


    @Override
    public List<Feedback> findAllFeedbacksByUser(int userId) {
        return feedbackRepository.findAllFeedbacksByUser(userId);
    }

    @Override
    public double getAverageRatingValueForUser(int userId) {
        return feedbackRepository.getAverageRatingValueForUser(userId);
    }

    @Override
    public List<User> getTopRatedUsers() {
        return feedbackRepository.getTopRatedUsers();
    }

    @Override
    public List<User> getTopRatedPassengers() {
        return feedbackRepository.getTopRatedPassengers();
    }

    @Override
    public List<Integer> getRatingValuesForUser(int userId) {
        return feedbackRepository.getRatingValuesForUser(userId);
    }




    @Override
    public List<Feedback> getAll(FeedbackFilterOptions feedbackFilterOptions) {
        return feedbackRepository.getAll(feedbackFilterOptions);
    }

    @Override
    public void delete(int id, User user) {
        Feedback feedback = feedbackRepository.getFeedbackById(id);
        if (user.isBlocked()) {
            throw new UnauthorizedOperationException("You`re blocked!!!");
        } else if (!(user.isAdmin() || feedback.getRatedUser().getUsername().equals(user.getUsername()))) {
            throw new UnauthorizedOperationException("You're not authorized for this operation");
        }
        feedbackRepository.delete(id);
    }


    @Override
    public void createFeedbackForDriver(Feedback feedback, Trip trip, User votingUser) {
        User ratedUser = trip.getCreatedBy();
        List<User> passengersInTrip = userService.showAllPassengersInTrip(trip.getTripId());
        if (hasUserRatedAnotherUser(votingUser.getId(), ratedUser.getId())) {
            throw new UnauthorizedOperationException("You can rate this driver only once.");
        }
        if (!isVotingUserInPassengers(passengersInTrip, votingUser)) {
            throw new UnauthorizedOperationException("Passenger not part of this trip");
        }
        validateTripStatus(trip);
        int feedbackRating = feedback.getRatingValue();
        if (feedbackRating < 0 || feedbackRating > 5) {
            throw new IllegalArgumentException("Feedback rating must be between 0 and 5.");
        } else if (!isCurrentTripPartOfFinishedTrips(trip)) {
            throw new UnauthorizedOperationException("The current trip is not part of finished trips.");
        } else if (votingUser.equals(ratedUser)) {
            throw new UnauthorizedOperationException("You're not authorized to perform this operation!");
        } else {
            feedback.setUserByCreatedBy(votingUser);
            feedback.setRatedUser(ratedUser);
            feedbackRepository.create(feedback);
        }
    }

    public void createFeedbackForPassenger(Feedback feedback, TripRequest tripRequest, User driver, int id) {
        Trip trip = tripRequest.getTrip();
        User passenger = userService.getById(id);
        if (!isVotingUserTheSameDriver(driver, trip)) {
            throw new UnauthorizedOperationException("You cannot create a feedback as you are not the creator of this trip");
        }
        List<User> passengers = userService.showAllPassengersInTrip(trip.getTripId());
        if (!isPassengerInTrip(passengers, passenger)) {
            throw new UnauthorizedOperationException("Passenger not part of this trip");
        }
        validateTripStatus(trip);
        int feedbackRating = feedback.getRatingValue();
        if (feedbackRating < 0 || feedbackRating > 5) {
            throw new IllegalArgumentException("Feedback rating must be between 0 and 5.");
        } else if (!isCurrentTripPartOfFinishedTrips(trip)) {
            throw new UnauthorizedOperationException("The current trip is not part of finished trips.");
        } else if (driver.equals(passenger)) {
            throw new UnauthorizedOperationException("You're not authorized to perform this operation!");
        } else if (hasUserRatedAnotherUser(driver.getId(), passenger.getId())) {
            throw new UnauthorizedOperationException("You can rate this passenger only once.");
        }
        feedback.setUserByCreatedBy(driver);
        feedback.setRatedUser(passenger);
        feedbackRepository.create(feedback);
    }

    @Override
    public boolean hasUserRatedAnotherUser(int userId, int ratedUserId) {
        return feedbackRepository.hasUserRatedAnotherUser(userId,ratedUserId);
    }


    public boolean isVotingUserInPassengers(List<User> passengersInTrip, User votingUser) {
        int votingUserId = votingUser.getId();
        for (User passenger : passengersInTrip) {
            if (votingUserId == passenger.getId()) {
                return true;
            }
        }
        return false;
    }

    public void validateTripStatus(Trip trip) {
        if (!trip.getTripStatus().equals(TripStatus.FINISHED)) {
            throw new UnauthorizedOperationException("This trip has not finished yet.");
        }
    }

    public boolean isCurrentTripPartOfFinishedTrips(Trip currentTrip) {
        List<Trip> finishedTrips = tripService.getAllCompletedTrips();

        for (Trip finishedTrip : finishedTrips) {
            if (finishedTrip.getTripId() == currentTrip.getTripId()) {
                return true;
            }
        }
        return false;
    }

    public boolean isVotingUserTheSameDriver(User votingUser, Trip trip) {
        int votingUserId = votingUser.getId();
        int driverId = trip.getCreatedBy().getId();
        if (votingUserId == driverId) {
            return true;
        }
        return false;
    }

    private boolean isPassengerInTrip(List<User> passengersInTrip, User passenger) {
        return passengersInTrip.stream().anyMatch(p -> p.getId() == passenger.getId());
    }

}

