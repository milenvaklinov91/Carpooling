package com.telerikacademy.carpooling.repositories.interfaces;

import com.telerikacademy.carpooling.models.User;

import java.util.List;

public interface UserRepository {
    List<User> getAll();

    Long countAllUsers();

    User getUserById(int id);

    User getByUsername(String username);

    User getByFirstName(String firstName);
    User getByLastname(String lastName);

    User getByEmail(String email);
    void create(User user);
    void update(User user);
    void delete(int id);

}
