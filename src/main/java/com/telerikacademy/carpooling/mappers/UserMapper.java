package com.telerikacademy.carpooling.mappers;

import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.dtos.UserDto;
import com.telerikacademy.carpooling.services.interfaces.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
private final UserService userService;

    public UserMapper(UserService userService) {
        this.userService = userService;
    }
    public User fromDto(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPhone_number(userDto.getPhone_number());
        return user;
    }
}
