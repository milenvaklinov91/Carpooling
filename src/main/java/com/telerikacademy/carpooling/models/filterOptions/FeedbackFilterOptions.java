package com.telerikacademy.carpooling.models.filterOptions;

import com.telerikacademy.carpooling.models.User;

import java.util.Optional;

public class FeedbackFilterOptions {
    private Optional<Integer> ratingValue = Optional.empty();
    private Optional<String> sortBy;
    private Optional<String> sortOrder;
    private Optional<String> topRatedDrivers;
    private Optional<String> topRatedPassengers;


    public FeedbackFilterOptions() {
        this(null, null, null,null, null);
    }

    public FeedbackFilterOptions(Integer ratingValue, String sortBy, String sortOrder, String topRatedDrivers, String topRatedPassengers) {
        this.ratingValue = Optional.ofNullable(ratingValue);
        this.sortBy = Optional.ofNullable(sortBy);
        this.sortOrder = Optional.ofNullable(sortOrder);
        this.topRatedDrivers = Optional.ofNullable(topRatedDrivers);
        this.topRatedPassengers = Optional.ofNullable(topRatedPassengers);
    }

    public Optional<String> getTopRatedDrivers() {
        return topRatedDrivers;
    }

    public void setTopRatedDrivers(Optional<String> topRatedDrivers) {
        this.topRatedDrivers = topRatedDrivers;
    }

    public Optional<String> getTopRatedPassengers() {
        return topRatedPassengers;
    }

    public void setTopRatedPassengers(Optional<String> topRatedPassengers) {
        this.topRatedPassengers = topRatedPassengers;
    }

    public Optional<Integer> getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(Optional<Integer> ratingValue) {
        this.ratingValue = ratingValue;
    }

    public Optional<String> getSortBy() {
        return sortBy;
    }

    public void setSortBy(Optional<String> sortBy) {
        this.sortBy = sortBy;
    }

    public Optional<String> getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Optional<String> sortOrder) {
        this.sortOrder = sortOrder;
    }
}
