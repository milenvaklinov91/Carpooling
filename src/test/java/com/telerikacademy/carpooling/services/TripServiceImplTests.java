package com.telerikacademy.carpooling.services;

import com.telerikacademy.carpooling.exceptions.UnauthorizedOperationException;
import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.enums.TripStatus;
import com.telerikacademy.carpooling.models.filterOptions.TripFilterOptions;
import com.telerikacademy.carpooling.repositories.TripRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TripServiceImplTests {
    @Mock
    TripRepositoryImpl tripRepository;
    @InjectMocks
    TripServiceImpl tripService;

    @Test
    public void getTripById_Should_ReturnTrip_When_MatchExist() {
        Trip mockTrip = Helper.createMockTrip();
        Mockito.when(tripRepository.getTripById(Mockito.anyInt()))
                .thenReturn(mockTrip);

        Trip result = tripService.getTripById(mockTrip.getTripId());

        Assertions.assertEquals(mockTrip, result);
    }

    @Test
    void getAllUsers_Should_Return_ListOfUsers() {
        List<Trip> expectedTrips = new ArrayList<>();
        TripFilterOptions tripFilterOptions = Helper.createMockTripFilterOptions();
        when(tripRepository.getAll(tripFilterOptions))
                .thenReturn(expectedTrips);

        List<Trip> result = tripService.getAll(tripFilterOptions);

        assertEquals(expectedTrips, result);
    }

    @Test
    void getAllCompletedTrips_Should_Return_ListOfCompletedTrips() {
        List<Trip> expectedCompletedTrips = new ArrayList<>();
        when(tripRepository.getAllCompletedTrips())
                .thenReturn(expectedCompletedTrips);

        List<Trip> result = tripService.getAllCompletedTrips();

        assertEquals(expectedCompletedTrips, result);
    }

    @Test
    void createTrip_Should_CallRepositoryCreate_When_UserIsDriverAndNotBlocked() {
        Trip mockTrip = Helper.createMockTrip();
        User mockUser = Helper.createMockUser();
        mockUser.setIsDriver(true);
        mockUser.setIsBlocked(false);
        doNothing().when(tripRepository).create(mockTrip);

        tripService.create(mockTrip, mockUser);

        verify(tripRepository, times(1)).create(mockTrip);
    }

    @Test
    void createTrip_Should_ThrowUnauthorizedException_When_UserIsNotDriver() {
        Trip mockTrip = Helper.createMockTrip();
        User mockUser = Helper.createMockUser();
        mockUser.setIsDriver(false);

        assertThrows(UnauthorizedOperationException.class, () -> tripService.create(mockTrip, mockUser));
    }

    @Test
    void createTrip_Should_ThrowUnauthorizedException_When_UserIsBlocked() {
        Trip mockTrip = Helper.createMockTrip();
        User mockUser = Helper.createMockUser();
        mockUser.setIsDriver(true);
        mockUser.setIsBlocked(true);

        assertThrows(UnauthorizedOperationException.class, () -> tripService.create(mockTrip, mockUser));
    }

    @Test
    void modifyTrip_Should_CallRepositoryModify_When_UserIsAuthorized() {
        Trip mockTrip = Helper.createMockTrip();
        User mockUser = Helper.createMockUser();
        mockUser.setIsBlocked(false);

        mockTrip.setCreatedBy(mockUser);

        tripService.modify(mockTrip, mockUser);

        verify(tripRepository, times(1)).modify(mockTrip);
    }

    @Test
    void modifyTrip_Should_ThrowUnauthorizedException_When_UserIsBlocked() {
        Trip mockTrip = Helper.createMockTrip();
        User mockUser = Helper.createMockUser();

        mockUser.setIsBlocked(true);

        assertThrows(UnauthorizedOperationException.class, () -> tripService.modify(mockTrip, mockUser));
    }

    @Test
    void modifyTrip_Should_ThrowUnauthorizedException_When_UserIsNotAuthorized() {
        Trip mockTrip = Helper.createMockTrip();
        User mockUser = Helper.createMockUser();
        User mockUser2 = Helper.createSecondMockUser();

        mockTrip.setCreatedBy(mockUser);

        assertThrows(UnauthorizedOperationException.class, () -> tripService.modify(mockTrip, mockUser2));
    }

    @Test
    public void delete_ShouldThrowException_WhenUserIsBlocked() {
        User mockUser = Helper.createMockUser();
        mockUser.setIsBlocked(true);
        Trip mockTrip = Helper.createMockTrip();

        assertThrows(UnauthorizedOperationException.class, () -> {
            tripService.delete(mockTrip.getTripId(), mockUser);
        });

        verify(tripRepository, never()).delete(anyInt());
    }

    @Test
    public void delete_ShouldThrowException_WhenUserIsNotAdminOrCreator() {
        User mockUser = Helper.createMockUser();
        mockUser.setIsBlocked(false);
        mockUser.setAdmin(false);

        Trip mockTrip = Helper.createMockTrip();
        User secondMockTrip = Helper.createSecondMockUser();
        mockTrip.setCreatedBy(secondMockTrip);

        when(tripRepository.getTripById(anyInt())).thenReturn(mockTrip);

        assertThrows(UnauthorizedOperationException.class, () -> {
            tripService.delete(mockTrip.getTripId(), mockUser);
        });

        verify(tripRepository, never()).delete(anyInt());
    }

    @Test
    public void delete_ShouldDeleteTrip_WhenUserIsAdmin() {
        User mockUser = Helper.createMockUser();
        mockUser.setIsBlocked(false);
        mockUser.setAdmin(true);

        Trip mockTrip = Helper.createMockTrip();
        User secondMockUser = Helper.createSecondMockUser();
        mockTrip.setCreatedBy(secondMockUser);

        when(tripRepository.getTripById(anyInt())).thenReturn(mockTrip);

        tripService.delete(mockTrip.getTripId(), mockUser);

        verify(tripRepository, times(1)).delete(1);
    }

    @Test
    public void delete_ShouldDeleteTrip_WhenUserIsCreator() {
        User mockUser = Helper.createMockUser();
        mockUser.setIsBlocked(false);
        mockUser.setAdmin(false);

        Trip mockTrip = Helper.createMockTrip();
        User secondMockUser = Helper.createSecondMockUser();
        mockTrip.setCreatedBy(secondMockUser);

        when(tripRepository.getTripById(anyInt())).thenReturn(mockTrip);

        tripService.delete(mockTrip.getTripId(), secondMockUser);

        verify(tripRepository, times(1)).delete(1);
    }

    @Test
    public void inProgressTripStatus_ShouldUpdateStatus_WhenUserIsCreator() {
        User creatorUser = Helper.createMockUser();
        creatorUser.setIsBlocked(false);
        creatorUser.setAdmin(false);

        Trip mockTrip = Helper.createMockTrip();
        mockTrip.setCreatedBy(creatorUser);

        tripService.inProgressTripStatus(mockTrip, creatorUser);

        verify(tripRepository, times(1)).modify(mockTrip);
        assertEquals(TripStatus.INPROGRESS, mockTrip.getTripStatus());
    }

    @Test
    public void inProgressTripStatus_ShouldThrowException_WhenUserIsNotCreator() {
        User regularUser = Helper.createMockUser();
        regularUser.setIsBlocked(false);
        regularUser.setAdmin(false);

        Trip mockTrip = Helper.createMockTrip();
        User secondMockUser = Helper.createSecondMockUser();
        mockTrip.setCreatedBy(secondMockUser);

        assertThrows(UnauthorizedOperationException.class, () -> {
            tripService.inProgressTripStatus(mockTrip, regularUser);
        });

        verify(tripRepository, never()).modify(mockTrip);
    }

    @Test
    public void finishedTripStatus_ShouldUpdateStatus_WhenUserIsCreator() {
        User mockUser = Helper.createMockUser();
        mockUser.setIsBlocked(false);
        mockUser.setAdmin(false);

        Trip mockTrip = Helper.createMockTrip();
        mockTrip.setCreatedBy(mockUser);

        tripService.finishedTripStatus(mockTrip, mockUser);

        verify(tripRepository, times(1)).modify(mockTrip);
        assertEquals(TripStatus.FINISHED, mockTrip.getTripStatus());
    }

    @Test
    public void finishedTripStatus_ShouldThrowException_WhenUserIsNotCreator() {
        User mockUser = Helper.createMockUser();
        mockUser.setIsBlocked(false);
        mockUser.setAdmin(false);

        Trip mockTrip = Helper.createMockTrip();
        User secondMockUser = Helper.createSecondMockUser();
        mockTrip.setCreatedBy(secondMockUser);

        assertThrows(UnauthorizedOperationException.class, () -> {
            tripService.finishedTripStatus(mockTrip, mockUser);
        });

        verify(tripRepository, never()).modify(mockTrip);
    }

}
