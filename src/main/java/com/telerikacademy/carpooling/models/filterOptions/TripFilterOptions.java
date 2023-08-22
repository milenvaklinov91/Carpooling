package com.telerikacademy.carpooling.models.filterOptions;

import java.util.Optional;

public class TripFilterOptions {
    private Optional<String> startLocation;
    private Optional<String> endLocation;
    private Optional<String> departureTime;
    private Optional<String> costPerPerson;
    private Optional<String> username;
    private Optional<String> sortBy;
    private Optional<String> sortOrder;

    public TripFilterOptions() {
        this(null, null, null,
                null,null, null, null);
    }

    public TripFilterOptions(String startLocation, String endLocation,
                             String departureTime, String costPerPerson,
                             String username,
                             String sortBy, String sortOrder) {
        this.startLocation = Optional.ofNullable(startLocation);
        this.endLocation = Optional.ofNullable(endLocation);
        this.departureTime = Optional.ofNullable(departureTime);
        this.costPerPerson = Optional.ofNullable(costPerPerson);
        this.username = Optional.ofNullable(username);
        this.sortBy = Optional.ofNullable(sortBy);
        this.sortOrder = Optional.ofNullable(sortOrder);
    }

    public Optional<String> getStartLocation() {
        return startLocation;
    }

    public Optional<String> getEndLocation() {
        return endLocation;
    }

    public Optional<String> getDepartureTime() {
        return departureTime;
    }

    public Optional<String> getCostPerPerson() {
        return costPerPerson;
    }

    public Optional<String> getUsername() {
        return username;
    }

    public Optional<String> getSortBy() {
        return sortBy;
    }

    public Optional<String> getSortOrder() {
        return sortOrder;
    }

}


