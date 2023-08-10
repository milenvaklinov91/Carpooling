package com.telerikacademy.carpooling.models;

public class Car {

    private int carId;
    private int driverId;
    private String brand;
    private String model;
    private String colour;
    private String year;
    private String description;
    private boolean isExtraStorage;
    private boolean isSmoke;
    private boolean isAirConditioner;
    private boolean isPetAvailable;
    private boolean isConsumeFood;
    private boolean isConsumeDrink;

    public Car() {
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

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

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
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
}
