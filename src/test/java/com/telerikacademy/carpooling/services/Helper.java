package com.telerikacademy.carpooling.services;

import com.telerikacademy.carpooling.models.User;
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
}
