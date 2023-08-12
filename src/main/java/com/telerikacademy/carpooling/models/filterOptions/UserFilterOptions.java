package com.telerikacademy.carpooling.models.filterOptions;

import java.util.Optional;

public class UserFilterOptions {
    private Optional<String> username;
    private Optional<String> firstName;
    private Optional<String> lastName;
    private Optional<String> sortBy;
    private Optional<String> sortOrder;

    public UserFilterOptions() {
        this(null, null, null,null,null);
    }


    public UserFilterOptions(String username, String firstName, String lastName,String sortBy,String sortOrder) {
        this.username = Optional.ofNullable(username);
        this.firstName = Optional.ofNullable(firstName);
        this.lastName = Optional.ofNullable(lastName);
        this.sortBy = Optional.ofNullable(sortBy);
        this.sortOrder = Optional.ofNullable(sortOrder);
    }

    public Optional<String> getUsername() {
        return username;
    }

    public Optional<String> getFirstName() {
        return firstName;
    }

    public Optional<String> getLastName() {
        return lastName;
    }

    public Optional<String> getSortBy() {
        return sortBy;
    }

    public Optional<String> getSortOrder() {
        return sortOrder;
    }
}
