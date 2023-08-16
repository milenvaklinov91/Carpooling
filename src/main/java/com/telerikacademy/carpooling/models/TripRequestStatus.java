package com.telerikacademy.carpooling.models;

import javax.persistence.*;

@Entity
@Table(name = "trip_request_status")
public class TripRequestStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_request_status_id")
    private int tripRequestStatusId;
    @Column(name = "status")
    private String status;

    public TripRequestStatus() {
    }

    public int getTripRequestStatusId() {
        return tripRequestStatusId;
    }

    public void setTripRequestStatusId(int tripRequestStatusId) {
        this.tripRequestStatusId = tripRequestStatusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
