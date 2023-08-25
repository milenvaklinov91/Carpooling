package com.telerikacademy.carpooling.repositories.interfaces;

import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.filterOptions.UserFilterOptions;

import java.util.List;

public interface UserRepository {
    List<User> getAll(UserFilterOptions userFilterOptions);

    Long countAllUsers();

    User getUserById(int id);

    User getByUsername(String username);

    User getByFirstName(String firstName);
    User getByLastname(String lastName);

    User getByEmail(String email);
    User getByPhoneNumber(String phoneNumber);
    void create(User user);
    void update(User user);
    void delete(int id);
    List<User>  getAdmins();
    List<User>  getRegularUsers();
    List<User> getDrivers();
    User getDriverByUsername(String username);
    List<User> getPassengers();
    List<User> getAllPassengersbyTripId(int id);
    List<User> getAllAcceptedUsers();
}
