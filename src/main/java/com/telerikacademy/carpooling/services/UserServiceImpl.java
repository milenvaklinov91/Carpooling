package com.telerikacademy.carpooling.services;

import com.telerikacademy.carpooling.exceptions.EmailExitsException;
import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.exceptions.InvalidPasswordException;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.repositories.interfaces.UserRepository;
import com.telerikacademy.carpooling.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public List<User> getAll() {
        return userRepository.getAll();
    }

    public Long countAllUsers() {
        return userRepository.countAllUsers();
    }

    public User getById(int id) {   //admin or ownuser
        return userRepository.getUserById(id);
    }

    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    public User getByFirstName(String firstName) {
        return userRepository.getByFirstName(firstName);
    }

    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    public void create(User user) {
        isDuplicateUsername(user);
        isDuplicateEmail(user);
        validatePassword(user.getPassword());
        user.setRegistrationDate(LocalDateTime.now());
        userRepository.create(user);
    }

    private void validatePassword(String password) throws InvalidPasswordException {
        String regex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";

        if (!password.matches(regex)) {
            throw new InvalidPasswordException("Password must be at least 8 characters long and should contain at least one uppercase letter, one lowercase letter, one digit, and one special symbol");
        }
    }
    private void isDuplicateUsername(User user) {
        boolean duplicateExists = true;
        try {
            userRepository.getByUsername(user.getUsername());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }
        if (duplicateExists) {
            throw new EntityNotFoundException("User", user.getUsername());
        }
    }

    private void isDuplicateEmail(User user) {
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
}
