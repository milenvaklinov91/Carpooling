package com.telerikacademy.carpooling.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.telerikacademy.carpooling.models.enums.RequestStatus;

import javax.persistence.*;

@Entity
@Table(name = "trip_requests")
public class TripRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private int tripRequestId;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User passenger;

    /*@Column(name = "request_status", columnDefinition = "varchar(255) default 'PENDING'")
    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;*/

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "trip_request_status_id")
    private TripRequestStatus tripRequestStatus;

    public TripRequest() {
    }

    public int getTripRequestId() {
        return tripRequestId;
    }

    public void setTripRequestId(int requestId) {
        this.tripRequestId = requestId;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public User getPassenger() {
        return passenger;
    }

    public void setPassenger(User userCreatedBy) {
        this.passenger = userCreatedBy;
    }

    /*public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }*/

}
