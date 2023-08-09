package com.telerikacademy.carpooling.repositories.interfaces;

import com.telerikacademy.carpooling.models.User;

import java.util.List;

public interface UserRepository {
    List<User> getAll();

    Long countAllUsers();

    User getUserById(int id);

    User getByUsername(String username);

    User getByFirstName(String firstName);

    User getByEmail(String email);
}
