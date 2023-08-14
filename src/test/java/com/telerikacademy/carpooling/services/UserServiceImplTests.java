package com.telerikacademy.carpooling.services;

import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.filterOptions.UserFilterOptions;
import com.telerikacademy.carpooling.repositories.UserRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTests {
    @Mock
    UserRepositoryImpl userRepository;
    @InjectMocks
   UserServiceImpl userService;

    @Test
    void getAllUsers_Should_Return_ListOfUsers(){
        // Arrange
        List<User> expectedUsers = new ArrayList<>();
        UserFilterOptions filterOptions=Helper.createMockFilterOptions();
        when(userRepository.getAll(filterOptions))
                .thenReturn(expectedUsers);

        // Act
        List<User> result = userService.getAll(filterOptions);

        // Assert
        assertEquals(expectedUsers, result);
    }

    @Test
    public void countAllUsers_Should_Return_CountUsers(){
        //Arrange
        Long expectedCount = 1L;
        when(userRepository.countAllUsers()).thenReturn(expectedCount);

        //Act
        long actualCount = userService.countAllUsers();

        // Assert
        assertEquals(expectedCount, actualCount);
    }
    @Test
    public void getById_Should_ReturnUser_When_MatchExist() {
        //Arrange
        User mockUser = Helper.createMockUser();

        Mockito.when(userRepository.getUserById(Mockito.anyInt()))
                .thenReturn(mockUser);

        // Act
        User result = userService.getById(mockUser.getId());

        // Assert
        Assertions.assertEquals(mockUser, result);
    }
    @Test
    public void GetByUsername_Should_ReturnUser_When_MatchExist(){
        //Arrange
        User mockUser = Helper.createMockUser();

        Mockito.when(userRepository.getByUsername(Mockito.anyString()))
                .thenReturn(mockUser);

        // Act
        User result = userService.getByUsername(mockUser.getUsername());

        // Assert
        Assertions.assertEquals(mockUser, result);
    }
    @Test
    public void testGetByFirstName_Should_ReturnUser_When_MatchExist(){
        //Arrange
        User mockUser = Helper.createMockUser();

        Mockito.when(userRepository.getByFirstName(Mockito.anyString()))
                .thenReturn(mockUser);

        // Act
        User result = userService.getByFirstName(mockUser.getFirstName());

        // Assert
        Assertions.assertEquals(mockUser, result);
    }
    @Test
    public void testGetByLastName_Should_ReturnUser_When_MatchExist(){
        //Arrange
        User mockUser = Helper.createMockUser();

        Mockito.when(userRepository.getByLastname(Mockito.anyString()))
                .thenReturn(mockUser);

        // Act
        User result = userService.getByLastName(mockUser.getLastName());

        // Assert
        Assertions.assertEquals(mockUser, result);
    }
    @Test
    public void testGetByEmail_Should_ReturnUser_When_MatchExist(){
        //Arrange
        User mockUser = Helper.createMockUser();

        Mockito.when(userRepository.getByEmail(Mockito.anyString()))
                .thenReturn(mockUser);

        // Act
        User result = userService.getByEmail(mockUser.getEmail());

        // Assert
        Assertions.assertEquals(mockUser, result);
    }
    @Test
    public void testGetByPhone_Should_ReturnUser_When_MatchExist(){
        //Arrange
        User mockUser = Helper.createMockUser();

        Mockito.when(userRepository.getByPhoneNumber(Mockito.anyString()))
                .thenReturn(mockUser);

        // Act
        User result = userService.getByPhone(mockUser.getPhone_number());

        // Assert
        Assertions.assertEquals(mockUser, result);
    }
    @Test
    public void testGetAdmins_Should_ReturnUser_When_MatchExist(){
        //Arrange
        User mockUser = Helper.createMockUser();
        User mockUser1 = Helper.createMockUser();
        List<User> users=new ArrayList<>();
        users.add(mockUser);
        users.add(mockUser1);

        Mockito.when(userRepository.getAdmins())
                .thenReturn(users);

        // Act
        List<User> result = userService.getAdmins();

        // Assert
        Assertions.assertEquals(users, result);
    }
    @Test
    public void testGetRegular_Should_ReturnUser_When_MatchExist(){
        //Arrange
        User mockUser = Helper.createMockUser();
        User mockUser1 = Helper.createMockUser();
        mockUser.setAdmin(false);
        mockUser1.setAdmin(false);
        List<User> users=new ArrayList<>();
        users.add(mockUser);
        users.add(mockUser1);

        Mockito.when(userRepository.getRegularUsers())
                .thenReturn(users);

        // Act
        List<User> result = userService.getRegularUsers();

        // Assert
        Assertions.assertEquals(users, result);
    }
    @Test
    public void testGetDrivers_Should_ReturnUser_When_MatchExist(){
        //Arrange
        User mockUser = Helper.createMockUser();
        User mockUser1 = Helper.createMockUser();
        List<User> users=new ArrayList<>();
        users.add(mockUser);
        users.add(mockUser1);

        Mockito.when(userRepository.getDrivers())
                .thenReturn(users);

        // Act
        List<User> result = userService.getDrivers();

        // Assert
        Assertions.assertEquals(users, result);
    }
    @Test
    public void testGetPassengers_Should_ReturnUser_When_MatchExist(){
        //Arrange
        User mockUser = Helper.createMockUser();
        User mockUser1 = Helper.createMockUser();
        List<User> users=new ArrayList<>();
        mockUser.setIsDriver(false);
        mockUser1.setIsDriver(false);
        users.add(mockUser);
        users.add(mockUser1);

        Mockito.when(userRepository.getPassengers())
                .thenReturn(users);

        // Act
        List<User> result = userService.getPassengers();

        // Assert
        Assertions.assertEquals(users, result);
    }
    @Test
    public void testGetDriverByUsername_Should_ReturnUser_When_MatchExist(){
        //Arrange
        User mockUser = Helper.createMockUser();

        Mockito.when(userRepository.getDriverByUsername("MockUsername"))
                .thenReturn(mockUser);

        // Act
        User result = userService.getDriverByUsername(mockUser.getUsername());

        // Assert
        Assertions.assertEquals(mockUser, result);
    }
    @Test
    void createUser_Should_CreateUser_When_UserCreated() {
        User mockUser =Helper.createMockUser();
        mockUser.setPassword("Draganch0!");

        Mockito.when(userRepository.getByUsername(mockUser.getUsername())).thenThrow(EntityNotFoundException.class);
        Mockito.when(userRepository.getByEmail(mockUser.getEmail())).thenThrow(EntityNotFoundException.class);
        Mockito.when(userRepository.getByPhoneNumber(mockUser.getPhone_number())).thenThrow(EntityNotFoundException.class);

        userService.create(mockUser);

        Mockito.verify(userRepository, times(1)).create(mockUser);
        assertEquals(LocalDateTime.now().getDayOfYear(), mockUser.getRegistrationDate().getDayOfYear());
    }
}
