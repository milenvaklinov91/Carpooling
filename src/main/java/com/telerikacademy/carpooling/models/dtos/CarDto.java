package com.telerikacademy.carpooling.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.telerikacademy.carpooling.models.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CarDto {
//    private String carPhoto;
    @NotNull(message = "Brand can't be empty")
    @Size(min = 2, max = 20, message = "Username should be between 2 and 20 symbols")
    @NotBlank
    private String brand;
    @NotNull(message = "Model can't be empty")
    @Size(min = 1, max = 20, message = "Model should be between 1 and 20 symbols")
    @NotBlank
    private String model;
    @Size(min = 1, max = 7, message = "Car capacity should be between 1 and 7 seats")
    @NotBlank
    private Integer capacity;
    @NotNull(message = "Color can't be empty")
    @Size(min = 2, max = 20, message = "The color of the car should be between 2 and 20 symbols")
    @NotBlank
    private String colour;
    @NotNull(message = "Car year can't be empty")
    @Size(min = 1900, max = 2023, message = "The car year should be between 1900 and 2023.")
    @NotBlank
    private Integer year;
    @Size(min = 5, max = 255, message = "The car description should be between 5 and 255 symbols")
    @NotBlank
    private String description;
    private boolean isExtraStorage;
    private boolean isSmoke;
    private boolean isAirConditioner;
    private boolean isPetAvailable;
    private boolean isConsumeFood;
    private boolean isConsumeDrink;
}
