package com.telerikacademy.carpooling.mappers;

import com.telerikacademy.carpooling.services.emailServices.ConfirmationCodeGenerator;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.dtos.UserDto;
import com.telerikacademy.carpooling.repositories.interfaces.UserRepository;
import com.telerikacademy.carpooling.services.interfaces.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
private  UserService userService;
private UserRepository userRepository;
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
        if (userDto.getProfilePic() != null) {
            user.setProfilePic(userDto.getProfilePic());
        } else {
            user.setProfilePic("/images/profile_pics/profile.jpg");
        }
        user.setIsDriver(userDto.isDriver());
        String confirmationCode = ConfirmationCodeGenerator.generateCode();
        user.setConfirmationCode(confirmationCode);
        user.setStatus(1);
        return user;
    }

    public User fromUserDto(int id, UserDto userDto) {
        User user = fromDto(userDto);
        user.setId(id);
        return user;
    }

}
