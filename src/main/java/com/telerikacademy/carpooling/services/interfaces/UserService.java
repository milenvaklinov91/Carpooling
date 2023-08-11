package com.telerikacademy.carpooling.services.interfaces;

import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.dtos.UserDto;

import java.util.List;

public interface UserService {
    public List<User> getAll();


    Long countAllUsers();


     User getById(int id);

     User getByUsername(String username);

     User getByFirstName(String firstName);

    User getByEmail(String email);
    void create(User user);
}
