package com.telerikacademy.carpooling.services;

import com.telerikacademy.carpooling.exceptions.*;
import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.filterOptions.UserFilterOptions;
import com.telerikacademy.carpooling.repositories.interfaces.TripRepository;
import com.telerikacademy.carpooling.repositories.interfaces.UserRepository;
import com.telerikacademy.carpooling.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TripRepository tripRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TripRepository tripRepository) {
        this.userRepository = userRepository;
        this.tripRepository = tripRepository;
    }

    public List<User> getAll(UserFilterOptions userFilterOptions,User logUser) {
        if (!(logUser.isAdmin())) {
            throw new UnauthorizedOperationException("You're not authorized for this operation");
        }
        return userRepository.getAll(userFilterOptions);
    }

    public Long countAllUsers() {
        return userRepository.countAllUsers();
    }

    public User getById(int id) {

        return userRepository.getUserById(id);
    }

    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    public User getByFirstName(String firstName) {
        return userRepository.getByFirstName(firstName);
    }

    public User getByLastName(String lastName) {
        return userRepository.getByLastname(lastName);
    }

    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    public User getByPhone(String phone) {
        return userRepository.getByPhoneNumber(phone);
    }

    public List<User> getAdmins() {
        return userRepository.getAdmins();
    }

    public List<User> getRegularUsers() {
        return userRepository.getRegularUsers();
    }

    public List<User> getDrivers() {
        return userRepository.getDrivers();
    }

    public List<User> getPassengers() {
        return userRepository.getPassengers();
    }

    public User getDriverByUsername(String username) {
        return userRepository.getDriverByUsername(username);
    }

    public List<User> getAllApprovedUsers(User logUser) {
        if (!(logUser.isAdmin())) {
            throw new UnauthorizedOperationException("You're not authorized for this operation");
        }
       return userRepository.getAllAcceptedUsers();

    }


    public void create(User user) {
        isDuplicateUsername(user);
        isDuplicateEmail(user);
        isDuplicatePhoneNumber(user);
        validatePassword(user.getPassword());
        user.setRegistrationDate(LocalDateTime.now());
        user.setIsBlocked(false);
        user.setAdmin(false);
        userRepository.create(user);
    }

    public void update(User user, User logUser) {
        try {
            User existUser = userRepository.getUserById(user.getId());
            if (logUser.isBlocked()) {
                throw new UnauthorizedOperationException("You`re blocked!!!");
            }
            if (logUser.isAdmin() || existUser.getUsername().equals(logUser.getUsername())) {
                existUser.setUsername(user.getUsername());
                existUser.setLastName(user.getLastName());
                user.setProfilePic(user.getProfilePic());
                existUser.setPhone_number(user.getPhone_number());
                existUser.setEmail(user.getEmail());
                existUser.setPassword(user.getPassword());
                existUser.setIsDriver(user.isDriver());
                existUser.setStatus(user.getStatus());
                userRepository.update(existUser);
            } else {
                throw new UnauthorizedOperationException("You're not authorized for this operation");
            }
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }


    }

    public void delete(int id, User logUser) {
        if (!(logUser.isAdmin())) {
            throw new UnauthorizedOperationException("You're not authorized for this operation");
        }
        User deletedUser = userRepository.getUserById(id);
        deletedUser.setStatus(3);
        deletedUser.setIsBlocked(true);
        userRepository.update(deletedUser);
    }

    public void validatePassword(String password) throws InvalidPasswordException {
        String regex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";

        if (!password.matches(regex)) {
            throw new InvalidPasswordException();
        }
    }

    public void isDuplicateUsername(User user) {
        boolean duplicateExists = true;
        try {
            userRepository.getByUsername(user.getUsername());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }
        if (duplicateExists) {
            throw new EntityDuplicateException("User", user.getUsername());
        }
    }

    public void isDuplicateEmail(User user) {
        boolean duplicateEmail = true;
        try {
            userRepository.getByEmail(user.getEmail());
        } catch (EntityNotFoundException e) {
            duplicateEmail = false;
        }
        if (duplicateEmail) {
            throw new EmailExitsException(user.getEmail());
        }
    }

    private void isDuplicatePhoneNumber(User user) {
        boolean duplicatePhone = true;
        try {
            userRepository.getByPhoneNumber(user.getPhone_number());
        } catch (EntityNotFoundException e) {
            duplicatePhone = false;
        }
        if (duplicatePhone) {
            throw new EntityDuplicateException("Phone number", user.getPhone_number());
        }
    }

    public User getUserDetails(int id) {
        User admin = userRepository.getUserById(id);
        if (!admin.isAdmin()) {
            throw new UnauthorizedOperationException("You're not authorized for this operation!");
        }
        return admin;
    }


    public User blockUser(int id, User user) {
        User normalUser = userRepository.getUserById(id);
        if (user.isAdmin() && !normalUser.isAdmin()) {
            normalUser.setIsBlocked(true);
            userRepository.update(normalUser);
            return normalUser;
        }
        throw new UnauthorizedOperationException("You're not authorized for this operation!");
    }

    public User unBlockUser(int id, User user) {
        User normalUser = userRepository.getUserById(id);
        if (user.isAdmin() && !normalUser.isAdmin()) {
            normalUser.setIsBlocked(false);
            userRepository.update(normalUser);
            return normalUser;
        }
        throw new UnauthorizedOperationException("You're not authorized for this operation!");
    }

    public User makeAdmin(int id, User user) {
        User user1 = userRepository.getUserById(id);
        if (user.isAdmin() && !user1.isAdmin()) {
            user1.setAdmin(true);
            userRepository.update(user1);
            return user1;
        }
        throw new UnauthorizedOperationException("You're not authorized for this operation!");
    }

    public User demoteAdmin(int id, User user) {
        User user1 = userRepository.getUserById(id);
        if (user.isAdmin() && user1.isAdmin()) {
            user1.setAdmin(false);
            userRepository.update(user1);
            return user1;
        }
        throw new UnauthorizedOperationException("You're not authorized for this operation!");
    }

    public List<Trip> showTravelsByUser(int id) {
        return tripRepository.findAllTripsByUser(id);
    }

    public List<User> showAllPassengersInTrip(int id) {
        return userRepository.getAllPassengersbyTripId(id);
    }


}
