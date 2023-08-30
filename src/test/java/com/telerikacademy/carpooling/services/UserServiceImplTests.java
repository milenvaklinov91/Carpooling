package com.telerikacademy.carpooling.services;

import com.telerikacademy.carpooling.exceptions.EmailExitsException;
import com.telerikacademy.carpooling.exceptions.EntityDuplicateException;
import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.exceptions.UnauthorizedOperationException;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.filterOptions.UserFilterOptions;
import com.telerikacademy.carpooling.repositories.UserRepositoryImpl;
import com.telerikacademy.carpooling.repositories.interfaces.UserRepository;
import com.telerikacademy.carpooling.services.interfaces.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.telerikacademy.carpooling.services.Helper.createMockUser;
import static org.junit.jupiter.api.Assertions.*;
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
        User user=createMockUser();
        user.setAdmin(true);
        List<User> expectedUsers = new ArrayList<>();
        UserFilterOptions filterOptions=Helper.createMockFilterOptions();
        when(userRepository.getAll(filterOptions))
                .thenReturn(expectedUsers);

        // Act
        List<User> result = userService.getAll(filterOptions,user);

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
        User mockUser = createMockUser();

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
        User mockUser = createMockUser();

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
        User mockUser = createMockUser();

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
        User mockUser = createMockUser();

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
        User mockUser = createMockUser();

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
        User mockUser = createMockUser();

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
        User mockUser = createMockUser();
        User mockUser1 = createMockUser();
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
        User mockUser = createMockUser();
        User mockUser1 = createMockUser();
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
        User mockUser = createMockUser();
        User mockUser1 = createMockUser();
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
        User mockUser = createMockUser();
        User mockUser1 = createMockUser();
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
        User mockUser = createMockUser();

        Mockito.when(userRepository.getDriverByUsername("MockUsername"))
                .thenReturn(mockUser);

        // Act
        User result = userService.getDriverByUsername(mockUser.getUsername());

        // Assert
        Assertions.assertEquals(mockUser, result);
    }
    @Test
    void createUser_Should_CreateUser_When_UserCreated() {
        User mockUser = createMockUser();
        mockUser.setPassword("Draganch0!");

        Mockito.when(userRepository.getByUsername(mockUser.getUsername())).thenThrow(EntityNotFoundException.class);
        Mockito.when(userRepository.getByEmail(mockUser.getEmail())).thenThrow(EntityNotFoundException.class);
        Mockito.when(userRepository.getByPhoneNumber(mockUser.getPhone_number())).thenThrow(EntityNotFoundException.class);

        userService.create(mockUser);

        Mockito.verify(userRepository, times(1)).create(mockUser);
        assertEquals(LocalDateTime.now().getDayOfYear(), mockUser.getRegistrationDate().getDayOfYear());
    }
    @Test
    void createUser_Should_Throw_EntityDuplicateException_When_DuplicateUsernameExists() {
        User mockUser = createMockUser();
        Mockito.when(userRepository.getByUsername(mockUser.getUsername())).thenReturn(mockUser);

        assertThrows(EntityDuplicateException.class, () -> userService.create(mockUser));
        Mockito.verify(userRepository, Mockito.never()).create(mockUser);
    }

    @Test
    void createUser_Should_Throw_EmailExistsException_When_DuplicateEmailExists() {
        User mockUser = createMockUser();
        Mockito.when(userRepository.getByUsername(mockUser.getUsername())).thenThrow(EntityNotFoundException.class);
        Mockito.when(userRepository.getByEmail(mockUser.getEmail())).thenReturn(mockUser);

        assertThrows(EmailExitsException.class, () -> userService.create(mockUser));

        Mockito.verify(userRepository, Mockito.never()).create(mockUser);
    }

    @Test
    void createUser_Should_Throw_EntityDuplicateException_When_DuplicatePhoneExists() {
        User mockUser = createMockUser();


        assertThrows(EntityDuplicateException.class, () -> userService.create(mockUser));

        Mockito.verify(userRepository, Mockito.never()).create(mockUser);
    }

    @Test
    void deleteUser_Should_SetStatusToDeleted_When_UserIsAdmin() {
        int userId=2;
        User adminUser = createMockUser();
        User deletedUser = createMockUser();
        adminUser.setAdmin(true);

        when(userRepository.getUserById(userId)).thenReturn(deletedUser);

        userService.delete(userId, adminUser);

        verify(userRepository, times(1)).update(deletedUser);

        assertEquals(3, deletedUser.getStatus());
    }

    @Test
    void deleteUser_Should_ThrowException_When_UserIsNotAdmin() {
        int userId = 2;
        User nonAdminUser = createMockUser();
        nonAdminUser.setAdmin(false);

        assertThrows(UnauthorizedOperationException.class,
                () -> userService.delete(userId, nonAdminUser));

        verify(userRepository, never()).update(any());
    }

    @Test
    void updateUser_Should_UpdateUser_When_UserIsAuthorized() {
        User existingUser = createMockUser();
        User logedUser = createMockUser();
        logedUser.setAdmin(true);
        logedUser.setIsBlocked(false);

        User updatedUser = createMockUser();
        updatedUser.setId(existingUser.getId());
        updatedUser.setLastName("new_lastName");

        when(userRepository.getUserById(existingUser.getId())).thenReturn(existingUser);

        userService.update(updatedUser, logedUser);

        verify(userRepository, times(1)).update(existingUser);

        assertEquals(updatedUser.getUsername(), existingUser.getUsername());
        assertEquals(updatedUser.getLastName(), existingUser.getLastName());

    }


    @Test
    void updateUser_Should_ThrowException_When_UserIsBlocked() {
        User existingUser = createMockUser();
        User logedUser = createMockUser();
        logedUser.setAdmin(false);
        logedUser.setIsBlocked(true);

        assertThrows(UnauthorizedOperationException.class,
                () -> userService.update(existingUser, logedUser));

        verify(userRepository, never()).update(any());
    }

    @Test
    void updateUser_Should_ThrowException_When_UserNotFound() {
        User logedUser = createMockUser();
        logedUser.setAdmin(true);
        User updatedUser = new User(/* Initialize with updated values */);

        when(userRepository.getUserById(updatedUser.getId())).thenThrow(EntityNotFoundException.class);

        assertThrows(ResponseStatusException.class,
                () -> userService.update(updatedUser, logedUser));
    }

  
    @Test
    void blockUser_Should_BlockUser_When_UserIsAdminAndUserIsNotBlocked() {
        int userId = 1;
        User user = new User();
        user.setAdmin(true);

        User mockedUserFromRepository = new User();
        mockedUserFromRepository.setIsBlocked(false);
        when(userRepository.getUserById(userId)).thenReturn(mockedUserFromRepository);

        userService.blockUser(userId, user);

        assertTrue(mockedUserFromRepository.isBlocked());
        verify(userRepository, times(1)).update(mockedUserFromRepository);
    }

    @Test
    void blockUser_Should_ThrowException_When_UserIsNotAdmin() {
        User mockUser = createMockUser();
        User mockUserToBlock = createMockUser();
        mockUserToBlock.setIsBlocked(true);

        Mockito.when(userRepository.getUserById(mockUser.getId())).thenReturn(mockUser);

        Assertions.assertThrows(UnauthorizedOperationException.class, () -> userService.blockUser(mockUser.getId(), mockUserToBlock));

        Mockito.verify(userRepository, times(1)).getUserById(mockUser.getId());
        Mockito.verify(userRepository, Mockito.never()).update(mockUserToBlock);
    }

    @Test
    void unblockUser_Should_UnblockUser_When_UserIsAdminAndUnblockedUserIsBlocked() {
        int userId = 1;
        User user = new User();
        user.setAdmin(true);

        User mockedUserFromRepository = new User();
        mockedUserFromRepository.setIsBlocked(true);
        when(userRepository.getUserById(userId)).thenReturn(mockedUserFromRepository);

        userService.unBlockUser(userId, user);

        assertFalse(mockedUserFromRepository.isBlocked());
        verify(userRepository, times(1)).update(mockedUserFromRepository);

    }

    @Test
    void unblockUser_Should_ThrowException_When_UserIsNotAdmin() {
        User mockUser = createMockUser();
        User mockUserToBlock = createMockUser();

        Mockito.when(userRepository.getUserById(mockUser.getId())).thenReturn(mockUser);

        Assertions.assertThrows(UnauthorizedOperationException.class, () -> userService.unBlockUser(mockUser.getId(), mockUserToBlock));

        Mockito.verify(userRepository, times(1)).getUserById(mockUser.getId());
        Mockito.verify(userRepository, Mockito.never()).update(mockUserToBlock);
    }
    @Test
    void makeAdmin_Should_ThrowUnauthorizedOperationException_When_UserIsNotAdmin() {
        User mockUser1 = createMockUser();
        User mockUser = createMockUser();


        Mockito.when(userRepository.getUserById(mockUser1.getId())).thenReturn(mockUser1);
        Mockito.when(userRepository.getUserById(mockUser.getId())).thenReturn(mockUser);

        Assertions.assertThrows(UnauthorizedOperationException.class, () -> userService.makeAdmin(mockUser1.getId(), mockUser));

        Mockito.verify(userRepository, times(1)).getUserById(mockUser.getId());
        Mockito.verify(userRepository, times(1)).getUserById(mockUser1.getId());
        Mockito.verify(userRepository, Mockito.never()).update(any());
    }

    @Test
    public void testMakeAdmin_Success() {
        int userId = 1;
        User user = new User();
        user.setAdmin(true);

        User mockedUserFromRepository = new User();
        when(userRepository.getUserById(userId)).thenReturn(mockedUserFromRepository);

        userService.makeAdmin(userId, user);

        assertTrue(mockedUserFromRepository.isAdmin());
        verify(userRepository, times(1)).update(mockedUserFromRepository);
    }

    @Test
    public void testDemoteAdmin_Unauthorized() {
        int userId = 1;
        User user = new User();
        user.setAdmin(true);

        User mockedUserFromRepository = new User();
        when(userRepository.getUserById(userId)).thenReturn(mockedUserFromRepository);

        assertThrows(UnauthorizedOperationException.class, () -> userService.demoteAdmin(userId, user));

        assertFalse(mockedUserFromRepository.isAdmin());
        verify(userRepository, never()).update(any());
    }

    @Test
    void unMakeAdmin_Should_UnMakeUserAdmin_When_UserIsAdminAndUserIsAdmin() {
        User mockAdmin = createMockUser();
        User mockAdmin1 = createMockUser();
        mockAdmin.setAdmin(true);
        mockAdmin1.setAdmin(true);

        when(userRepository.getUserById(mockAdmin.getId())).thenReturn(mockAdmin);
        doNothing().when(userRepository).update(any());

        userService.demoteAdmin(mockAdmin.getId(), mockAdmin1);


        assertFalse(mockAdmin.isAdmin());
        verify(userRepository, times(1)).update(mockAdmin);
    }
    @Test
    void getUserDetails_Should_Return_UnauthorizedOperationException_When_UserIsNotAdmin() {
        User mockUser = createMockUser();
        mockUser.setAdmin(false);

        Mockito.when(userRepository.getUserById(mockUser.getId())).thenReturn(mockUser);

        Assertions.assertThrows(UnauthorizedOperationException.class, () ->
                userService.getUserDetails(mockUser.getId()));
        Mockito.verify(userRepository, times(1)).
                getUserById(mockUser.getId());
    }
}

