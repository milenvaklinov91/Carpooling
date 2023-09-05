package com.telerikacademy.carpooling.services;

import com.telerikacademy.carpooling.models.*;
import com.telerikacademy.carpooling.models.enums.TripRequestStatus;
import com.telerikacademy.carpooling.models.enums.TripStatus;
import com.telerikacademy.carpooling.models.filterOptions.TripFilterOptions;
import com.telerikacademy.carpooling.models.filterOptions.UserFilterOptions;

public class Helper {
    public static User createMockAdmin() {
        User mockUser = createMockUser();
        mockUser.setAdmin(true);
        return mockUser;
    }

    public static User createMockUser() {
        var mockUser = new User();
        mockUser.setId(1);
        mockUser.setUsername("MockUsername");
        mockUser.setPassword("MockPassword");
        mockUser.setLastName("MockLastName");
        mockUser.setFirstName("MockFirstName");
        mockUser.setEmail("mock@user.com");
        mockUser.setPhone_number("0888888888");
        mockUser.setIsDriver(true);
        mockUser.setAdmin(true);
        mockUser.setIsBlocked(true);
        mockUser.setIsDriver(true);
        mockUser.setCar(createMockCar());
        return mockUser;
    }

    public static User createSecondMockUser() {
        var mockUser = new User();
        mockUser.setId(1);
        mockUser.setUsername("Username");
        mockUser.setPassword("Password");
        mockUser.setLastName("LastName");
        mockUser.setFirstName("FirstName");
        mockUser.setEmail("mock2@user.com");
        mockUser.setPhone_number("0888888887");
        mockUser.setIsDriver(false);
        mockUser.setAdmin(false);
        mockUser.setIsBlocked(false);
        mockUser.setIsDriver(false);
        return mockUser;
    }

    public static UserFilterOptions createMockFilterOptions() {
        return new UserFilterOptions("username",
                "firstName",
                "lastName",
                "sortBy",
                "orderBy"
                );
    }

    public static Car createMockCar(){
        Car car = new Car();
        car.setDriver_license("test");
        car.setBrand("test");
        car.setModel("test");
        car.setCapacity(4);
        car.setColour("test");
        car.setYear(2000);
        car.setDescription("test");
        car.setExtraStorage(false);
        car.setSmoke(false);
        car.setAirConditioner(false);
        car.setPetAvailable(false);
        car.setConsumeFood(false);
        car.setConsumeDrink(false);
        return car;
    }

    public static Trip createMockTrip() {
        var mockTrip = new Trip();
        mockTrip.setTripId(1);
        mockTrip.setStartLocation("MockStartLocation");
        mockTrip.setEndLocation("MockEndLocation");
        mockTrip.setDepartureTime("MockLastName");
        mockTrip.setCostPerPerson("MockCostPerPerson");
        mockTrip.setAvailableSeats(4);
        mockTrip.setDescription("MockDescription");
        mockTrip.setTripStatus(TripStatus.AWAITING);
        mockTrip.setCreatedBy(createMockUser());

        return mockTrip;
    }

    public static TripFilterOptions createMockTripFilterOptions() {
        return new TripFilterOptions(
                "startLocation",
                "endLocation",
                "departureTime",
                "costPerPerson",
                "username",
                "sortBy",
                "sortOrder"
        );
    }

    public static TripRequest createMockTripRequest() {
        var mockTripRequest = new TripRequest();
        mockTripRequest.setTripRequestId(1);
        mockTripRequest.setTrip(createMockTrip());
        mockTripRequest.setTripRequestStatus(TripRequestStatus.PENDING);

        return mockTripRequest;
    }
    public static TripRequest secondCreateMockTripRequest() {
        var mockTripRequest = new TripRequest();
        mockTripRequest.setTripRequestId(1);
        mockTripRequest.setTrip(createMockTrip());
        mockTripRequest.setTripRequestStatus(TripRequestStatus.APPROVED);

        return mockTripRequest;
    }

    public static TripRequest thirdCreateMockTripRequest() {
        var mockTripRequest = new TripRequest();
        mockTripRequest.setTripRequestId(1);
        mockTripRequest.setTrip(createMockTrip());
        mockTripRequest.setTripRequestStatus(TripRequestStatus.REJECTED);

        return mockTripRequest;
    }

    public static Feedback createMockFeedback(){
        var mockFeedback = new Feedback();
        mockFeedback.setFeedbackId(1);
        mockFeedback.setRatedUser(createMockUser());
        mockFeedback.setUserByCreatedBy(createMockUser());
        mockFeedback.setRatingValue(5);
        mockFeedback.setTripId(1);
        return mockFeedback;
    }

    public static FeedbackComment createFeedbackComment(){
        var mockFeedbackComment = new FeedbackComment();
        mockFeedbackComment.setFeedbackCommentId(1);
        mockFeedbackComment.setComment("Comment");
        mockFeedbackComment.setUserCreatedBy(createMockUser());
        mockFeedbackComment.setFeedback(createMockFeedback());
        return mockFeedbackComment;
    }

}
