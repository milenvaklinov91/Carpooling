package com.telerikacademy.carpooling.services;

import com.telerikacademy.carpooling.exceptions.SeatException;
import com.telerikacademy.carpooling.exceptions.UnauthorizedOperationException;
import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.models.TripRequest;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.enums.TripRequestStatus;
import com.telerikacademy.carpooling.repositories.interfaces.TripRepository;
import com.telerikacademy.carpooling.repositories.interfaces.TripRequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TripRequestServiceImplTests {

    @Mock
    TripRequestRepository tripRequestRepository;
    @Mock
    TripRepository tripRepository;
    @InjectMocks
    TripRequestServiceImpl tripRequestService;


    @Test
    public void getTripRequestById_Should_ReturnTripRequest_When_MatchExist() {
        TripRequest mockTripRequest = Helper.createMockTripRequest();
        when(tripRequestRepository.getTripRequestById(Mockito.anyInt()))
                .thenReturn(mockTripRequest);

        TripRequest result = tripRequestService.getTripRequestById(mockTripRequest.getTripRequestId());

        assertEquals(mockTripRequest, result);
    }

    @Test
    void getAllTripRequests_Should_Return_ListOfTripRequests() {
        List<TripRequest> expectedTripRequests = new ArrayList<>();
        when(tripRequestRepository.getAll())
                .thenReturn(expectedTripRequests);

        List<TripRequest> result = tripRequestService.getAll();

        assertEquals(expectedTripRequests, result);
    }

    @Test
    public void create_Should_CallRepositoryCreate_When_UserIsNotBlocked() {
        TripRequest mockTripRequest = Helper.createMockTripRequest();
        User mockUser = Helper.createMockUser();
        mockUser.setIsBlocked(false);

        tripRequestService.create(mockTripRequest, mockUser);

        verify(tripRequestRepository, times(1)).create(mockTripRequest);
    }

    @Test
    public void create_Should_ThrowUnauthorizedException_When_UserIsBlocked() {
        TripRequest mockTripRequest = Helper.createMockTripRequest();
        User mockUser = Helper.createMockUser();

        assertThrows(UnauthorizedOperationException.class, () -> {
            tripRequestService.create(mockTripRequest, mockUser);
        });

        verify(tripRequestRepository, never()).create(any(TripRequest.class));
    }

    @Test       //todo
    public void delete_Should_DeleteTripRequest_When_UserIsAdmin() {
        User mockUser = Helper.createMockUser();
        mockUser.setIsBlocked(false);
        mockUser.setAdmin(true);

        TripRequest mockTripRequest = Helper.createMockTripRequest();

        when(tripRequestRepository.getTripRequestById(anyInt())).thenReturn(mockTripRequest);

        tripRequestService.delete(mockTripRequest.getTripRequestId(), mockUser);

        verify(tripRequestRepository, times(1)).delete(1);
    }

    @Test
    public void delete_Should_ThrowUnauthorizedException_When_UserIsBlocked() {
        TripRequest mockTripRequest = Helper.secondCreateMockTripRequest();
        User mockUser = Helper.createMockUser();
        when(tripRequestRepository.getTripRequestById(anyInt()))
                .thenReturn(mockTripRequest);

        assertThrows(UnauthorizedOperationException.class, () -> {
            tripRequestService.delete(mockTripRequest.getTripRequestId(), mockUser);
        });
    }


    @Test
    public void delete_Should_ThrowUnauthorizedException_When_RequestIsNotApproved() {
        TripRequest mockTripRequest = Helper.thirdCreateMockTripRequest();
        User mockUser = Helper.createMockUser();
        when(tripRequestRepository.getTripRequestById(anyInt()))
                .thenReturn(mockTripRequest);

        assertThrows(UnauthorizedOperationException.class, () -> {
            tripRequestService.delete(mockTripRequest.getTripRequestId(), mockUser);
        });
    }

    @Test
    public void delete_Should_ThrowUnauthorizedException_When_UserIsNotAuthorized() {
        TripRequest mockTripRequest = Helper.createMockTripRequest();
        User mockUser = Helper.createMockUser();
        mockUser.setIsBlocked(false);
        User secondMockUser = Helper.createSecondMockUser();
        mockTripRequest.setPassenger(mockUser);

        when(tripRequestRepository.getTripRequestById(anyInt())).thenReturn(mockTripRequest);

        assertThrows(UnauthorizedOperationException.class, () -> {
            tripRequestService.delete(mockTripRequest.getTripRequestId(), secondMockUser);
        });
    }

    @Test
    public void rejectTripRequest_Should_CallIncreaseAvailableSeats_And_SetStatus_When_RequestIsApproved() {
        TripRequest mockTripRequest = Helper.createMockTripRequest();
        User mockUser = Helper.createMockUser();
        mockUser.setIsBlocked(false);
        mockTripRequest.getTrip().setAvailableSeats(2);

        mockTripRequest.setTripRequestStatus(TripRequestStatus.APPROVED);

        when(tripRequestRepository.getTripRequestById(anyInt())).thenReturn(mockTripRequest);
        tripRequestService.rejectTripRequest(mockTripRequest, mockUser);

        verify(tripRequestRepository, times(1)).modify(mockTripRequest);
    }

    @Test
    public void rejectTripRequest_Should_SetStatus_When_RequestIsNotApproved() {
        TripRequest mockTripRequest = Helper.createMockTripRequest();
        User mockUser = Helper.createMockUser();
        mockUser.setIsBlocked(false);

        mockTripRequest.setTripRequestStatus(TripRequestStatus.PENDING);
        User secondMockUser = Helper.createSecondMockUser();

        when(tripRequestRepository.getTripRequestById(anyInt())).thenReturn(mockTripRequest);

        tripRequestService.rejectTripRequest(mockTripRequest, secondMockUser);

        verify(tripRequestRepository, times(1)).modify(mockTripRequest);
    }

    @Test
    public void approveTripRequest_Should_ReduceAvailableSeats_When_RequestIsApproved() {
        TripRequest mockTripRequest = Helper.createMockTripRequest();
        User mockUser = Helper.createMockUser();

        when(tripRequestRepository.getTripRequestById(anyInt()))
                .thenReturn(mockTripRequest);

        tripRequestService.approveTripRequest(mockTripRequest, mockUser);

        verify(tripRequestRepository, times(1)).modify(mockTripRequest);
        verify(tripRepository, times(1)).modify(mockTripRequest.getTrip());
    }

    @Test
    public void delete_Should_IncreaseAvailableSeats_When_TripRequestIsApproved() {
        TripRequest mockTripRequest = Helper.createMockTripRequest();
        mockTripRequest.setTripRequestStatus(TripRequestStatus.APPROVED);
        User mockUser = Helper.createMockUser();
        mockUser.setIsBlocked(false);
        Trip trip = mockTripRequest.getTrip();

        tripRequestService.delete(mockTripRequest.getTripRequestId(), mockUser);

        verify(tripRequestService, times(1)).increaseAvailableSeats(trip);
    }

    @Test
    public void ReduceAvailableSeats_Should_TrowSeatException_When_NoAvailableSeats() {
        Trip mockTrip = Helper.createMockTrip();
        mockTrip.setAvailableSeats(0);

        assertThrows(SeatException.class, () -> tripRequestService.reduceAvailableSeats(mockTrip));
    }

    @Test
    public void IncreaseAvailableSeats_Should_TrowSeatException_When_ReachedMaxCapacity() {
        User mockUser = Helper.createMockUser();
        Trip mockTrip = Helper.createMockTrip();
        mockTrip.setAvailableSeats(mockUser.getCar().getCapacity());

        assertThrows(SeatException.class, () -> tripRequestService.increaseAvailableSeats(mockTrip));
    }

    @Test
    public void SetStatus_Should_TrowUnauthorizedOperationException_When_UserIsNotAuthorized() {
        User mockUser = Helper.createMockUser();
        TripRequest mockTripRequest = Helper.createMockTripRequest();
        mockTripRequest.getTrip().setCreatedBy(mockUser);
        User secondMockUser = Helper.createSecondMockUser();

        assertThrows(UnauthorizedOperationException.class, () -> {
            tripRequestService.setStatus(mockTripRequest, secondMockUser, TripRequestStatus.APPROVED);
        });
    }
}
