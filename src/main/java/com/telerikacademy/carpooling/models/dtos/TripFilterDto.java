package com.telerikacademy.carpooling.models.dtos;

public class TripFilterDto {
    private String startLocation;
    private String endLocation;
    private String departureTime;
    private String costPerPerson;
    private String username;
    private String sortBy;
    private String sortOrder;

    public TripFilterDto() {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}
