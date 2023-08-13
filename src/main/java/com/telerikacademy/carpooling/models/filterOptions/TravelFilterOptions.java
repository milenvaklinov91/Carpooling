package com.telerikacademy.carpooling.models.filterOptions;

import java.util.Optional;

public class TravelFilterOptions {
    private Optional<String> startLocation;
    private Optional<String> endLocations;
    private Optional<String> departureTime;
    private Optional<String> costPerPerson;
    private Optional<String> sortBy;
    private Optional<String> sortOrder;

    public TravelFilterOptions() {
        this(null, null, null,
                null, null, null);
    }

    public TravelFilterOptions(String startLocation, String endLocations,
                               String departureTime, String costPerPerson,
                               String sortBy, String sortOrder) {
        this.startLocation = Optional.ofNullable(startLocation);
        this.endLocations = Optional.ofNullable(endLocations);
        this.departureTime = Optional.ofNullable(departureTime);
        this.costPerPerson = Optional.ofNullable(costPerPerson);
        this.sortBy = Optional.ofNullable(sortBy);
        this.sortOrder = Optional.ofNullable(sortOrder);
    }

    public Optional<String> getStartLocation() {
        return startLocation;
    }

    public Optional<String> getEndLocations() {
        return endLocations;
    }

    public Optional<String> getDepartureTime() {
        return departureTime;
    }

    public Optional<String> getCostPerPerson() {
        return costPerPerson;
    }

    public Optional<String> getSortBy() {
        return sortBy;
    }

    public Optional<String> getSortOrder() {
        return sortOrder;
    }
}


