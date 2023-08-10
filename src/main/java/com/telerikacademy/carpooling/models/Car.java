package com.telerikacademy.carpooling.models;

import javax.persistence.*;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private int carId;
    @Column(name = "driver_id")
    private int driverId;
    @Column(name = "car_brand")
    private String brand;
    @Column(name = "car_model")
    private String model;
    @Column(name = "car_color")
    private String colour;
    @Column(name = "car_year")
    private String year;
    @Column(name = "description")
    private String description;
    @Column(name = "extra_storage")
    private boolean isExtraStorage;
    @Column(name = "smoke")
    private boolean isSmoke;
    @Column(name = "air_conditioner")
    private boolean isAirConditioner;
    @Column(name = "pet_available")
    private boolean isPetAvailable;
    @Column(name = "consume_food")
    private boolean isConsumeFood;
    @Column(name = "consume_drink")
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
