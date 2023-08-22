package com.telerikacademy.carpooling.controllers.rest;

import com.telerikacademy.carpooling.controllers.AuthenticationHelper;
import com.telerikacademy.carpooling.exceptions.*;
import com.telerikacademy.carpooling.mappers.UserMapper;
import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.dtos.UserDto;
import com.telerikacademy.carpooling.models.filterOptions.UserFilterOptions;
import com.telerikacademy.carpooling.services.emailServices.EmailService;
import com.telerikacademy.carpooling.services.interfaces.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;
    private final AuthenticationHelper authenticationHelper;
    private final UserMapper userMapper;
    private final EmailService emailService;

    public UserController(UserService service, AuthenticationHelper authenticationHelper, UserMapper userMapper, EmailService emailService) {
        this.service = service;
        this.authenticationHelper = authenticationHelper;
        this.userMapper = userMapper;
        this.emailService = emailService;
    }

    @GetMapping
    public List<User> getAll(@RequestParam(required = false) String username,
                             @RequestParam(required = false) String firstName,
                             @RequestParam(required = false) String lastName,
                             @RequestParam(required = false) String sortBy,
                             @RequestParam(required = false) String sortOrder) {
        UserFilterOptions userFilterOptions = new UserFilterOptions(username, firstName, lastName, sortBy, sortOrder);
        return service.getAll(userFilterOptions);
    }

    @GetMapping("/count")
    public Long countAllUser() {
        return service.countAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        try {
            return service.getById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/username")
    public User getUserByUsername(@RequestHeader HttpHeaders headers, @RequestHeader String username) {
        try {
            return service.getByUsername(username);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/firstname")
    public User getUserByFirstName(@RequestHeader HttpHeaders headers, @RequestHeader String firstName) {
        try {
            return service.getByFirstName(firstName);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/lastname")
    public User getUserByLastName(@RequestHeader HttpHeaders headers, @RequestHeader String lastName) {
        try {
            return service.getByLastName(lastName);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/email")
    public User getUserByEmail(@RequestHeader HttpHeaders headers, @RequestHeader String email) {
        try {
            return service.getByEmail(email);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/phone")
    public User getUserByPhoneNum(@RequestHeader HttpHeaders headers, @RequestHeader String phoneNum) {
        try {
            return service.getByPhone(phoneNum);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/admins")
    public List<User> getAdmins(@RequestHeader HttpHeaders headers) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            service.getUserDetails(user.getId());
            return service.getAdmins();
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/regular")
    public List<User> getRegularUsers(@RequestHeader HttpHeaders headers) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            service.getUserDetails(user.getId());
            return service.getRegularUsers();
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/drivers")
    public List<User> getDrivers() {
        try {
            return service.getDrivers();
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/drivers/{username}")
    public User getDriverByUsername(@RequestHeader String username) {
        try {
            return service.getDriverByUsername(username);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/passengers")
    public List<User> getPassengers() {
        try {
            return service.getPassengers();
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{userId}/travels")
    public List<Trip> getUserTravels(@PathVariable int userId) {
        return service.showTravelsByUser(userId);
    }

    @GetMapping("/{tripId}/passengers")
    public List<User> getAllPassengersByTrip(@PathVariable int tripId) {
        return service.showAllPassengersInTrip(tripId);
    }

    @PostMapping
    public User create(@Valid @RequestBody UserDto userDto) {
        try {
            User user = userMapper.fromDto(userDto);
            emailService.sendConfirmationEmail(user.getEmail(), user.getConfirmationCode());
            service.create(user);
            return user;
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirmUser(@RequestHeader HttpHeaders headers, @RequestParam String email, @RequestParam String confirmationCode) {
        User user = service.getByEmail(email);
        User logUser = authenticationHelper.tryGetUser(headers);
        if (user != null && user.getConfirmationCode().equals(confirmationCode)) {
            user.setStatus(2);
            service.update(user, logUser);
            return ResponseEntity.ok("User confirmed successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid confirmation code or email.");
        }
    }

    @PutMapping("/{id}")
    public String update(@RequestHeader HttpHeaders headers, @PathVariable int id,
                         @Valid @RequestBody UserDto userDto) {
        try {
            User logUser = authenticationHelper.tryGetUser(headers);
            User user = userMapper.fromUserDto(id, userDto);
            service.update(user, logUser);
            return "User was successfully updated!";
        } catch (DuplicatePasswordException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}/block")
    public User blockUser(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return service.blockUser(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicatePasswordException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}/unblock")
    public User unBlockUser(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return service.unBlockUser(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicatePasswordException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}/make-admin")
    public User makeAdmin(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return service.makeAdmin(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicatePasswordException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}/unmake-admin")
    public User unMakeAdmin(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return service.demoteAdmin(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicatePasswordException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        User user = authenticationHelper.tryGetUser(headers);
        service.delete(id, user);
        return "User was successfully deleted!";
    }

}
