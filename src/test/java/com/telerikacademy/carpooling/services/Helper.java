package com.telerikacademy.carpooling.services;

import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.models.TripRequest;
import com.telerikacademy.carpooling.models.User;
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

}
