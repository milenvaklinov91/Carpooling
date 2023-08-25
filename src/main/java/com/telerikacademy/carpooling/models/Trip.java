package com.telerikacademy.carpooling.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.telerikacademy.carpooling.models.enums.TripStatus;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "trips")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private int tripId;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;
    @Column(name = "start_location")
    private String startLocation;
    @Column(name = "end_location")
    private String endLocation;
    @Column(name = "departure_datetime")
    private String departureTime;
    @Column(name = "cost_per_person")
    private String costPerPerson;
    @Column(name = "available_seats")
    private int availableSeats;
    @Column(name = "description")
    private String description;
    @Column(name = "duration")
    private String duration = "test";
    @Column(name = "distance")
    private String distance = "test";

    @JsonIgnore
    @OneToMany(mappedBy = "trip", fetch = FetchType.EAGER)
    private List<TripRequest> tripRequest;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private TripStatus tripStatus;

    public Trip() {
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getCostPerPerson() {
        return costPerPerson;
    }

    public void setCostPerPerson(String costPerPerson) {
        this.costPerPerson = costPerPerson;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public List<TripRequest> getTripRequest() {
        return tripRequest;
    }

    public void setTripRequest(List<TripRequest> tripRequest) {
        this.tripRequest = tripRequest;
    }

    public TripStatus getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(TripStatus tripStatus) {
        this.tripStatus = tripStatus;
    }
}
