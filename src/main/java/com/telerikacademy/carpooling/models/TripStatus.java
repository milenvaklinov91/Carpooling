package com.telerikacademy.carpooling.models;

import javax.persistence.*;


@Entity
@Table(name = "trip_status")
public class TripStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_status_id")
    private int tripStatusId;
    @Column(name = "status")
    private String status;

    public TripStatus() {
    }

    public int getTripStatusId() {
        return tripStatusId;
    }

    public void setTripStatusId(int tripStatusId) {
        this.tripStatusId = tripStatusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
