package com.telerikacademy.carpooling.services;

import com.telerikacademy.carpooling.exceptions.UnauthorizedOperationException;
import com.telerikacademy.carpooling.models.Feedback;
import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.repositories.FeedbackRepositoryImpl;
import com.telerikacademy.carpooling.repositories.TripRepositoryImpl;
import com.telerikacademy.carpooling.services.interfaces.FeedbackService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class FeedbackServiceImplTests {

    @Mock
    FeedbackRepositoryImpl feedbackRepository;
    @InjectMocks
    FeedbackServiceImpl feedbackService;

    @Test
     void getFeedbackById_Should_ReturnFeedback_When_MatchExists() {
        Feedback mockFeedback = Helper.createMockFeedback();
        Mockito.when(feedbackRepository.getFeedbackById(Mockito.anyInt()))
                .thenReturn(mockFeedback);

        Feedback result = feedbackService.getFeedbackById(mockFeedback.getFeedbackId());

        Assertions.assertEquals(mockFeedback, result);

    }
    @Test
    void getAllFeedbacksByUserId_Should_Return_ListOfAllFeedbacksByUserId(){
        List<Feedback> allFeedbacksByUser = new ArrayList<>();
        User mockUser = Helper.createMockUser();
        when(feedbackRepository.findAllFeedbacksByUser(mockUser.getId()))
                .thenReturn(allFeedbacksByUser);

        List<Feedback> result = feedbackService.findAllFeedbacksByUser(mockUser.getId());

        assertEquals(allFeedbacksByUser, result);
    }
        @Test
        void getAverageFeedback_Should_Return_AverageFeedbackByUserId() {
            User mockUser = Helper.createMockUser();
            int userId = 1;
            double expectedAverageRating = 4.5;
            Mockito.when(feedbackRepository.getAverageRatingValueForUser(mockUser.getId()))
                    .thenReturn(expectedAverageRating);

            double result = feedbackService.getAverageRatingValueForUser(userId);

            Assertions.assertEquals(expectedAverageRating, result, 0.01);

        }
    @Test
    void getTopRatedUsers_Should_ReturnListOfTopRatedDrivers() {
        List<User> topRatedUsers = new ArrayList<>();
        Mockito.when(feedbackRepository.getTopRatedUsers()).thenReturn(topRatedUsers);
        List<User> result = feedbackService.getTopRatedUsers();
        Assertions.assertEquals(topRatedUsers, result);
    }
    @Test
    void getTopRatedPassengers_Should_ReturnListOfTopRatedPassengers() {
        List<User> topRatedPassengers = new ArrayList<>();
        Mockito.when(feedbackRepository.getTopRatedPassengers()).thenReturn(topRatedPassengers);
        List<User> result = feedbackService.getTopRatedPassengers();
        Assertions.assertEquals(topRatedPassengers, result);
    }

    @Test
    void getRatingValuesForUser_Should_ReturnListOfIntegers() {
        int userId = 1;
        List<Integer> ratingValues = Arrays.asList(4, 5, 4, 3, 5);
        Mockito.when(feedbackRepository.getRatingValuesForUser(userId)).thenReturn(ratingValues);
        List<Integer> result = feedbackService.getRatingValuesForUser(userId);
        Assertions.assertEquals(ratingValues, result);
    }

    @Test
    public void delete_ShouldDeleteFeedback_WhenUserIsOwner() {
        User mockUser = Helper.createMockUser();
        mockUser.setIsBlocked(false);
        mockUser.setAdmin(false);

        Feedback mockFeedback = Helper.createMockFeedback();

        when(feedbackRepository.getFeedbackById(anyInt())).thenReturn(mockFeedback);

        feedbackService.delete(mockFeedback.getFeedbackId(), mockUser);

        verify(feedbackRepository, times(1)).delete(1);
    }
    @Test
    public void delete_ShouldDeleteFeedback_WhenUserIsAdmin() {
        User mockUser = Helper.createMockUser();
        mockUser.setIsBlocked(false);
        mockUser.setAdmin(true);

        Feedback mockFeedback = Helper.createMockFeedback();
        User secondMockUser = Helper.createSecondMockUser();
        mockFeedback.setUserByCreatedBy(secondMockUser);

        when(feedbackRepository.getFeedbackById(anyInt())).thenReturn(mockFeedback);

        feedbackService.delete(mockFeedback.getFeedbackId(), mockUser);

        verify(feedbackRepository, times(1)).delete(1);
    }
    @Test
    public void delete_ShouldThrowException_WhenUserIsNotAdminOrCreator() {
        User mockUser = Helper.createMockUser();
        mockUser.setIsBlocked(false);
        mockUser.setAdmin(false);

        Feedback mockFeedback = Helper.createMockFeedback();
        User secondMockUser = Helper.createSecondMockUser();
        mockFeedback.setUserByCreatedBy(secondMockUser);

        when(feedbackRepository.getFeedbackById(anyInt())).thenReturn(mockFeedback);

        assertThrows(UnauthorizedOperationException.class, () -> {
            feedbackService.delete(mockFeedback.getFeedbackId(), mockUser);
        });

        verify(feedbackRepository, never()).delete(anyInt());
    }

    @Test
    public void delete_ShouldThrowException_WhenUserIsBlocked() {
        User mockUser = Helper.createMockUser();
        mockUser.setIsBlocked(true);
        Feedback mockFeedback = Helper.createMockFeedback();

        assertThrows(UnauthorizedOperationException.class, () -> {
            feedbackService.delete(mockFeedback.getFeedbackId(), mockUser);
        });

        verify(feedbackRepository, never()).delete(anyInt());
    }
//    @Test
//    public void createFeedbackForDriver_ShouldCreateFeedback_WhenAllConditionsAreMet() {
//        User driver = Helper.createMockUser();
//        User passenger = Helper.createSecondMockUser();
//        Trip trip = Helper.createMockTrip(driver);
//
//        Feedback feedback = Helper.createMockFeedback(passenger, driver);
//
//        when(feedbackRepository.hasUserRatedAnotherUser(anyInt(), anyInt())).thenReturn(false);
//        when(userService.showAllPassengersInTrip(anyInt())).thenReturn(Collections.singletonList(passenger));
//        when(tripService.getAllCompletedTrips()).thenReturn(Collections.singletonList(trip));
//        when(feedbackRepository.create(feedback)).thenReturn(feedback);
//
//        assertDoesNotThrow(() -> feedbackService.createFeedbackForDriver(feedback, trip, passenger));
//    }
//
//    @Test
//    public void createFeedbackForDriver_ShouldThrowUnauthorizedException_WhenPassengerHasRatedBefore() {
//        User driver = Helper.createMockUser();
//        User passenger = Helper.createSecondMockUser();
//        Trip trip = Helper.createMockTrip(driver);
//
//        Feedback feedback = Helper.createMockFeedback(passenger, driver);
//
//        when(feedbackRepository.hasUserRatedAnotherUser(anyInt(), anyInt())).thenReturn(true);
//
//        assertThrows(UnauthorizedOperationException.class, () -> feedbackService.createFeedbackForDriver(feedback, trip, passenger));
//    }
//
//    @Test
//    public void createFeedbackForDriver_ShouldThrowUnauthorizedException_WhenPassengerIsNotInTrip() {
//        User driver = Helper.createMockUser();
//        User passenger = Helper.createSecondMockUser();
//        Trip trip = Helper.createMockTrip(driver);
//
//        Feedback feedback = Helper.createMockFeedback(passenger,driver);
//
//        when(feedbackRepository.hasUserRatedAnotherUser(anyInt(), anyInt())).thenReturn(false);
//        when(userService.showAllPassengersInTrip(anyInt())).thenReturn(Collections.emptyList());
//
//        assertThrows(UnauthorizedOperationException.class, () -> feedbackService.createFeedbackForDriver(feedback, trip, passenger));
//    }






}
