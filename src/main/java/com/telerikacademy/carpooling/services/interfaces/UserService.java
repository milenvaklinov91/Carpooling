package com.telerikacademy.carpooling.services.interfaces;

import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.filterOptions.UserFilterOptions;

import java.util.List;

public interface UserService {
    public List<User> getAll(UserFilterOptions userFilterOptions);


    Long countAllUsers();


     User getById(int id);

     User getByUsername(String username);

     User getByFirstName(String firstName);
    User getByLastName(String lastName);
    User getByPhone(String phone);
    List<User>  getAdmins();
    List<User>  getRegularUsers();
    List<User> getDrivers();
    User getDriverByUsername(String username);
    List<User> getPassengers();
    User getByEmail(String email);
    void create(User user);
    void update(User user,User logUser);
    void delete(int id,User logUser);
    User getUserDetails(int id);
    User blockUser(int id, User user);
    User unBlockUser(int id, User user);
    User makeAdmin(int id, User user);
    User demoteAdmin(int id, User user);
    List<Trip> showTravelsByUser(int id);
    List<User> showAllPassengersInTrip(int id);

}
