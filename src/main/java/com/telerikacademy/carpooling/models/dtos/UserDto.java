package com.telerikacademy.carpooling.models.dtos;

import javax.validation.Valid;
import javax.validation.constraints.*;


public class UserDto {
    @NotNull(message = "Username can't be empty")
    @Size(min = 2, max = 20, message = "Username should be between 2 and 20 symbols")
    @NotBlank(message="Required field!")
    private String username;
    @NotBlank(message="Required field!")
    @Size(min = 8, message="Password must be at least 8 characters long and should contain " +
            "at least one uppercase letter, one lowercase letter, one digit, and one special symbol")
    private String password;
    @NotNull(message = "Name can't be empty")
    @Size(min = 2, max = 20, message = "Name should be between 2 and 20 symbols")
    @NotBlank(message="Required field!")
    private String firstName;
    @NotNull(message = "Last name can't be empty")
    @Size(min = 2, max = 20, message = "Last name should be between 2 and 20 symbols")
    @NotBlank(message="Required field!")
    private String lastName;
    @NotNull(message = "Email can't be empty")
    @Email(message = "Email is invalid")
    @NotBlank(message="Required field!")
    @Valid
    private String email;
    @NotBlank(message="Required field!")
    @NotNull(message = "You need to provide a valid phone number")
    private String phone_number;

    private String profilePic;
    private boolean isDriver;


    public UserDto() {
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
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

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public boolean isDriver() {
        return isDriver;
    }

    public void setDriver(boolean driver) {
        isDriver = driver;
    }
}


