package com.telerikacademy.carpooling.models.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TripDto {

    @NotNull(message = "StartLocation can't be empty")
    @NotBlank
    private String startLocation;
    @NotNull(message = "EndLocation can't be empty")
    @NotBlank
    private String endLocation;
    @NotNull(message = "DepartureTime can't be empty")
    @NotBlank
    private String departureTime;
    @NotNull(message = "CostPerPerson can't be empty")
    @NotBlank
    private String costPerPerson;
    @NotNull(message = "AvailableSeats can't be empty")
    /*@NotBlank*/
    private int availableSeats;
    @NotNull(message = "Description can't be empty")
    @NotBlank
    private String description;

    public TripDto() {
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
}

