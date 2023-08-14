package com.telerikacademy.carpooling.models.dtos;

public class RatingDto {

    private int ratingId;
    private int tripId;
    private int ratingValue;
    private String comment;
    private int ratedUserId;
    private int userByCreatedById;

    public RatingDto() {
    }

    public int getRatingId() {
        return ratingId;
    }
    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
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

    public int getRatedUserId() {
        return ratedUserId;
    }

    public void setRatedUserId(int ratedUserId) {
        this.ratedUserId = ratedUserId;
    }

    public int getUserByCreatedById() {
        return userByCreatedById;
    }

    public void setUserByCreatedById(int userByCreatedById) {
        this.userByCreatedById = userByCreatedById;
    }
}

