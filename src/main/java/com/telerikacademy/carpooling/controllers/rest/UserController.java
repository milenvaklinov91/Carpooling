package com.telerikacademy.carpooling.controllers.rest;

import com.telerikacademy.carpooling.controllers.AuthenticationHelper;
import com.telerikacademy.carpooling.exceptions.EntityDuplicateException;
import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.mappers.UserMapper;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.dtos.UserDto;
import com.telerikacademy.carpooling.services.interfaces.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

    public UserController(UserService service, AuthenticationHelper authenticationHelper, UserMapper userMapper) {
        this.service = service;
        this.authenticationHelper = authenticationHelper;
        this.userMapper = userMapper;
    }
    @GetMapping
    public List<User> getAll() {
        return service.getAll();
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
    public User getUserByUsername(@RequestHeader HttpHeaders headers, String username) {
        try {
            return service.getByUsername(username);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/firstname")
    public User getUserByFirstName(@RequestHeader HttpHeaders headers, String firstName) {
        try {
            return service.getByFirstName(firstName);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/email")
    public User getUserByEmail(@RequestHeader HttpHeaders headers, String email) {
        try {
            return service.getByEmail(email);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
  @PostMapping
    public User create(@Valid @RequestBody UserDto userDto) {
        try {
            User user = userMapper.fromDto(userDto);
            service.create(user);
            return user;
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

}
