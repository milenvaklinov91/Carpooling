package com.telerikacademy.carpooling.services;

import com.telerikacademy.carpooling.exceptions.EmailExitsException;
import com.telerikacademy.carpooling.exceptions.EntityDuplicateException;
import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.exceptions.InvalidPasswordException;
import com.telerikacademy.carpooling.mappers.UserMapper;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.dtos.UserDto;
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

    public User getByLastName(String lastName) {
        return userRepository.getByLastname(lastName);
    }

    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    public void create(User user) {
        isDuplicateUsername(user);
        isDuplicateEmail(user);
        validatePassword(user.getPassword());
        user.setRegistrationDate(LocalDateTime.now());
        user.setIs_blocked(false);
        user.setIs_driver(false);
        userRepository.create(user);
    }

    public void update(User user) {
        try {
            User existUser = userRepository.getUserById(user.getId());
            existUser.setUsername(user.getUsername());
            existUser.setLastName(user.getLastName());
            if (user.getProfilePic() != null) {
                user.setProfilePic(user.getProfilePic());
            }
            existUser.setPhone_number(user.getPhone_number());
            existUser.setEmail(user.getEmail());
            existUser.setPassword(user.getPassword());
            userRepository.update(existUser);
        } catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }


    }
    public void delete(int id) {
        User user = userRepository.getUserById(id);
        userRepository.delete(id);
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
            throw new EntityDuplicateException("User", user.getUsername());
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
