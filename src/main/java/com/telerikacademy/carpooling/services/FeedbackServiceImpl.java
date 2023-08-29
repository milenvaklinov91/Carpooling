package com.telerikacademy.carpooling.services;

import com.telerikacademy.carpooling.exceptions.UnauthorizedOperationException;
import com.telerikacademy.carpooling.models.Feedback;
import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.filterOptions.FeedbackFilterOptions;
import com.telerikacademy.carpooling.repositories.interfaces.FeedbackRepository;
import com.telerikacademy.carpooling.services.interfaces.FeedbackService;
import com.telerikacademy.carpooling.services.interfaces.UserService;
import org.springframework.stereotype.Service;
import com.telerikacademy.carpooling.models.TripRequest;


import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserService userService;

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, UserService userService) {
        this.feedbackRepository = feedbackRepository;
        this.userService = userService;
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


//    @Override
//    public List<Feedback> getRatingOfUser(int userId) {
//        return feedbackRepository.getRatingOfUser(userId);
//    }


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
        boolean isVotingUserInPassengers = false;
        for (User passenger : passengersInTrip) {
            if (votingUser.getUsername().equals(passenger.getUsername())) {
                isVotingUserInPassengers = true;
                break;
            } throw new UnauthorizedOperationException("You cannot vote as you have not participated in this rating");
        }
        if (isVotingUserInPassengers) {
            if (!(trip.getTripStatus().equals("FINISHED"))) {
                throw new UnauthorizedOperationException("This trip has not finished yet.");
            }
            int feedbackRating = feedback.getRatingValue();
            if (feedbackRating < 0 || feedbackRating > 5) {
                throw new IllegalArgumentException("Feedback rating must be between 0 and 5.");
            }
            if (votingUser.equals(ratedUser)) {
                throw new UnauthorizedOperationException("You're not authorized to perform this operation!");
            } else {
                feedback.setUserByCreatedBy(votingUser);
                feedback.setRatedUser(ratedUser);
                feedbackRepository.create(feedback);
            }
        }
   }

    @Override
    public void createFeedbackForPassenger(Feedback feedback, TripRequest tripRequest, User user, int id) {

    }

    @Override
    public boolean hasUserRatedAnotherUser(int userId, int ratedUserId) {
        return false;
    }

    @Override
    public boolean isVotingUserTheSameDriver(User votingUser, Trip trip) {
        return false;
    }

    public void createFeedbackForPassenger(Feedback feedback, TripRequest tripRequest, User passenger) {
        Trip trip = tripRequest.getTrip();
        User driver = trip.getCreatedBy();
        int feedbackRating = feedback.getRatingValue();
        if (feedbackRating < 0 || feedbackRating > 5) {
            throw new IllegalArgumentException("Feedback rating must be between 0 and 5.");
        }

        if (passenger.equals(driver)) {
            throw new UnauthorizedOperationException("You're not authorized to perform this operation!");
        } else {
            feedback.setUserByCreatedBy(passenger);
            feedback.setRatedUser(driver);
            feedbackRepository.create(feedback);
        }
    }

    //todo да може да се дава само един рейтиинг на ID
    //todo да проверява дали този който дава рейтиг участва в трипа
}




// todo feedback for driver and feedback for passenger