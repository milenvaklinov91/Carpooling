package com.telerikacademy.carpooling.models.dtos;

import com.telerikacademy.carpooling.models.User;

public class FeedbackDto {

    private int tripId;
    private int ratingValue;
    private String comment;

    public FeedbackDto() {
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public int getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(int ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}

