package com.telerikacademy.carpooling.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "trip_requests")
public class TripRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private int requestId;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "travel_id")
    private Trip trip;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User passenger;
    @Column(name = "request_status")
    private String requestStatus;

    public TripRequest() {
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public Trip getTravel() {
        return trip;
    }

    public void setTravel(Trip tripId) {
        this.trip = tripId;
    }

    public User getPassenger() {
        return passenger;
    }

    public void setPassenger(User userCreatedBy) {
        this.passenger = userCreatedBy;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }
}
