package com.telerikacademy.carpooling.models.dtos;

import javax.validation.constraints.*;

public class CarDto {
    @NotNull(message = "Driver's License must be uploaded")
    private String driver_license;
    @NotNull(message = "Brand can't be empty")
    @Size(min = 2, max = 20, message = "Username should be between 2 and 20 symbols")
    @NotBlank
    private String brand;
    @NotNull(message = "Model can't be empty")
    @Size(min = 1, max = 20, message = "Model should be between 1 and 20 symbols")
    @NotBlank
    private String model;
    @NotNull(message = "Initial car capacity can't be empty")
    @Min(value = 1, message = "The car should have at least 1 space.")
    @Max(value = 7, message = "The car should have utmost 7 spaces.")
    private Integer capacity;
    @NotNull(message = "Color can't be empty")
    @Size(min = 2, max = 20, message = "The color of the car should be between 2 and 20 symbols")
    @NotBlank
    private String colour;
    @NotNull(message = "Car year can't be empty")
    @Min(value = 1900, message = "The car year should be at least 1900.")
    @Max(value = 2023, message = "The car year should be at most 2023.")
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isExtraStorage() {
        return isExtraStorage;
    }

    public void setExtraStorage(boolean extraStorage) {
        isExtraStorage = extraStorage;
    }

    public boolean isSmoke() {
        return isSmoke;
    }

    public void setSmoke(boolean smoke) {
        isSmoke = smoke;
    }

    public boolean isAirConditioner() {
        return isAirConditioner;
    }

    public void setAirConditioner(boolean airConditioner) {
        isAirConditioner = airConditioner;
    }

    public boolean isPetAvailable() {
        return isPetAvailable;
    }

    public void setPetAvailable(boolean petAvailable) {
        isPetAvailable = petAvailable;
    }

    public boolean isConsumeFood() {
        return isConsumeFood;
    }

    public void setConsumeFood(boolean consumeFood) {
        isConsumeFood = consumeFood;
    }

    public boolean isConsumeDrink() {
        return isConsumeDrink;
    }

    public void setConsumeDrink(boolean consumeDrink) {
        isConsumeDrink = consumeDrink;
    }

    public String getDriver_license() {
        return driver_license;
    }

    public void setDriver_license(String driver_license) {
        this.driver_license = driver_license;
    }
}