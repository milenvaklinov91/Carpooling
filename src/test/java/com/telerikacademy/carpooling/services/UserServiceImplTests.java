package com.telerikacademy.carpooling.services;

import com.telerikacademy.carpooling.repositories.UserRepositoryImpl;
import com.telerikacademy.carpooling.repositories.interfaces.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTests {
    @Mock
    UserRepositoryImpl userRepository;
    @InjectMocks
   UserServiceImpl userService;
}
