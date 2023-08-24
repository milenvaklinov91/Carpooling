package com.telerikacademy.carpooling.controllers.rest;

import com.telerikacademy.carpooling.controllers.AuthenticationHelper;
import com.telerikacademy.carpooling.exceptions.AuthorizationException;
import com.telerikacademy.carpooling.exceptions.DuplicatePasswordException;
import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.mappers.CarMapper;
import com.telerikacademy.carpooling.models.Car;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.dtos.CarDto;
import com.telerikacademy.carpooling.services.interfaces.CarService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;
    private final AuthenticationHelper authenticationHelper;
    private final CarMapper carMapper;

    public CarController(CarService carService, AuthenticationHelper authenticationHelper, CarMapper carMapper) {
        this.carService = carService;
        this.authenticationHelper = authenticationHelper;
        this.carMapper = carMapper;
    }
    @GetMapping("/check-options")
    public String checkOptions(
            @RequestParam(required = false) Boolean isExtraStorage,
            @RequestParam(required = false) Boolean isSmoke,
            @RequestParam(required = false) Boolean isAirConditioner,
            @RequestParam(required = false) Boolean isPetAvailable,
            @RequestParam(required = false) Boolean canConsumeFood,
            @RequestParam(required = false) Boolean canConsumeDrink
    ) {
        String result = "Options:";

        if (isExtraStorage != null) {
            result += " isExtraStorage=" + isExtraStorage;
        }
        if (isSmoke != null) {
            result += " isSmoke=" + isSmoke;
        }
        if (isAirConditioner != null) {
            result += " isAirConditioner=" + isAirConditioner;
        }
        if (isPetAvailable != null) {
            result += " isPetAvailable=" + isPetAvailable;
        }
        if (canConsumeFood != null) {
            result += " canConsumeFood=" + canConsumeFood;
        }
        if (canConsumeDrink != null) {
            result += " canConsumeDrink=" + canConsumeDrink;
        }

        return result;
    }
    @PostMapping
    public Car create(@RequestHeader HttpHeaders headers, @Valid @RequestBody CarDto carDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Car car = carMapper.fromCarDto(carDto);
            carService.create(car, user);
            return car;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public Car getCarById(@PathVariable int id) {
        try {
            return carService.getCarById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Car update(@RequestHeader HttpHeaders headers, @PathVariable int id,
                         @Valid @RequestBody CarDto carDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Car car = carMapper.fromCarDtoWithId(id, carDto);
            carService.update(car, user);
            return car;
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        User user = authenticationHelper.tryGetUser(headers);
        carService.delete(id, user);
    }

    @PutMapping("/{id}/extra-storage")
    public Car extraStorage(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return carService.extraStorage(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicatePasswordException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}/not-extra-storage")
    public Car notExtraStorage(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return carService.notExtraStorage(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicatePasswordException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}/smoke")
    public Car smoke(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return carService.smoke(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicatePasswordException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}/not-smoke")
    public Car notSmoke(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return carService.notSmoke(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicatePasswordException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}/airConditioner")
    public Car airConditioner(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return carService.airConditioner(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicatePasswordException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}/not-airConditioner")
    public Car notAirConditioner(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return carService.notAirConditioner(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicatePasswordException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}/pet-available")
    public Car petAvailable(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return carService.petAvailable(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicatePasswordException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}/not-pet-available")
    public Car notPetAvailable(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return carService.notPetAvailable(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicatePasswordException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}/consume-food")
    public Car consumeFood(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return carService.consumeFood(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicatePasswordException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}/not-consume-food")
    public Car notConsumeFood(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return carService.notConsumeFood(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicatePasswordException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}/consume-drink")
    public Car consumeDrink(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return carService.consumeDrink(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicatePasswordException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}/not-consume-drink")
    public Car notConsumeDrink(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return carService.notConsumeDrink(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicatePasswordException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

}
