package com.telerikacademy.carpooling.models.dtos;

public class TripRequestDto {

    private int tripId;
    private String requestStatus;

    public TripRequestDto() {
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }
}
