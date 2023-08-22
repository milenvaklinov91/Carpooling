package com.telerikacademy.carpooling.services;

import com.telerikacademy.carpooling.models.TripRequest;
import com.telerikacademy.carpooling.repositories.TripRequestRepositoryImpl;
import com.telerikacademy.carpooling.repositories.interfaces.TripRequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TripRequestServiceImplTests {

    @Mock
    TripRequestRepository tripRequestRepository;
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

}
