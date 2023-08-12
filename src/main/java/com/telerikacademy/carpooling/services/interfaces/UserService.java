package com.telerikacademy.carpooling.services.interfaces;

import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.dtos.UserDto;
import com.telerikacademy.carpooling.models.filterOptions.UserFilterOptions;

import java.util.List;

public interface UserService {
    public List<User> getAll(UserFilterOptions userFilterOptions);


    Long countAllUsers();


     User getById(int id);

     User getByUsername(String username);

     User getByFirstName(String firstName);
    User getByLastName(String lastName);
    List<User>  getAdmins();
    List<User>  getRegularUsers();
    User getByEmail(String email);
    void create(User user);
    void update(User user,User logUser);
    void delete(int id,User logUser);
    User getUserDetails(int id);
    User blockUser(int id, User user);
    User unBlockUser(int id, User user);
    User makeAdmin(int id, User user);
    User demoteAdmin(int id, User user);

}
