package com.telerikacademy.carpooling.models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "registration_date")
    private LocalDateTime registrationDate;
    @Column(name = "profile_picture")
    private String profilePic;
    @Column(name = "phone_number")
    private String phone_number;
    @Column(name = "is_driver")
    private boolean isDriver;
    @Column(name = "is_blocked")
    private boolean is_blocked;
    @Column(name = "is_admin")
    private boolean isAdmin;
    @JsonIgnore
    @OneToMany(mappedBy = "createdBy", fetch = FetchType.EAGER)
    private Set<Trip> trip;
    @JsonIgnore
    @OneToMany(mappedBy = "passenger", fetch = FetchType.EAGER)
    private Set<TripRequest> tripRequest;


    public User() {
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public boolean isDriver() {
        return isDriver;
    }

    public void setIsDriver(boolean is_driver) {
        this.isDriver = is_driver;
    }

    public boolean isBlocked() {
        return is_blocked;
    }

    public void setIs_blocked(boolean is_blocked) {
        this.is_blocked = is_blocked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Set<Trip> getTrip() {
        return trip;
    }

    public void setTrip(Set<Trip> trip) {
        this.trip = trip;
    }

    public Set<TripRequest> getTripRequest() {
        return tripRequest;
    }

    public void setTripRequest(Set<TripRequest> tripRequest) {
        this.tripRequest = tripRequest;
    }
}
