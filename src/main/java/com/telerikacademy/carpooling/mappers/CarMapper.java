package com.telerikacademy.carpooling.mappers;

import com.telerikacademy.carpooling.models.Car;
import com.telerikacademy.carpooling.models.dtos.CarDto;
import com.telerikacademy.carpooling.repositories.interfaces.CarRepository;
import com.telerikacademy.carpooling.services.interfaces.CarService;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

    private final CarRepository carRepository;
    private final CarService carService;

    public CarMapper(CarRepository carRepository, CarService carService) {
        this.carRepository = carRepository;
        this.carService = carService;
    }

    public Car fromCarDto(CarDto carDto){
        Car car = new Car();
        car.setDriver_license(carDto.getDriver_license());
        car.setBrand(carDto.getBrand());
        car.setModel(carDto.getModel());
        car.setCapacity(carDto.getCapacity());
        car.setColour(carDto.getColour());
        car.setYear(carDto.getYear());
        car.setDescription(carDto.getDescription());
        car.setExtraStorage(carDto.isExtraStorage());
        car.setSmoke(carDto.isSmoke());
        car.setAirConditioner(carDto.isAirConditioner());
        car.setPetAvailable(carDto.isPetAvailable());
        car.setConsumeFood(carDto.isConsumeFood());
        car.setConsumeDrink(carDto.isConsumeDrink());
        return car;
    }

    public Car fromCarDtoWithId(int id, CarDto carDto){
        Car car = fromCarDto(carDto);
        car.setCarId(id);
        Car carRepository = carService.getCarById(id);
        car.setUserCreatedBy(carRepository.getUserCreatedBy());
        return car;
    }
}
