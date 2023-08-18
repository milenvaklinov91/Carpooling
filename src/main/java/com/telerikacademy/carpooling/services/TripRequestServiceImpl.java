package com.telerikacademy.carpooling.services;

import com.telerikacademy.carpooling.exceptions.UnauthorizedOperationException;
import com.telerikacademy.carpooling.models.TripRequest;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.enums.TripRequestStatus;
import com.telerikacademy.carpooling.repositories.interfaces.TripRequestRepository;
import com.telerikacademy.carpooling.services.interfaces.TripRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TripRequestServiceImpl implements TripRequestService {

    private TripRequestRepository tripRequestRepository;

    @Autowired
    public TripRequestServiceImpl(TripRequestRepository tripRequestRepository) {
        this.tripRequestRepository = tripRequestRepository;
    }

    @Override
    public TripRequest getTripRequestById(int id) {
        return tripRequestRepository.getTripRequestById(id);
    }

    @Override
    public void create(TripRequest tripRequest, User user) {
        tripRequest.setPassenger(user);
        if (tripRequest.getPassenger().isBlocked()) {
            throw new UnauthorizedOperationException("You`re blocked!!!");
        }
        tripRequestRepository.create(tripRequest);
    }

    @Override
    public void delete(int id, User user) {
        TripRequest tripRequest = tripRequestRepository.getTripRequestById(id);
        if (user.isBlocked()) {
            throw new UnauthorizedOperationException("You`re blocked!!!");
        } else if (!(user.isAdmin() || tripRequest.getPassenger().getUsername().equals(user.getUsername()))) {
            throw new UnauthorizedOperationException("You're not authorized for this operation!");
        }
        tripRequestRepository.delete(id);
    }

    private void setStatus(TripRequest tripRequest, User user, TripRequestStatus status) {
        if (tripRequest.getTrip().getCreatedBy().getId() == user.getId()) {
            tripRequest.setTripRequestStatus(status);
            tripRequestRepository.modify(tripRequest);
        } else {
            throw new UnauthorizedOperationException("You're not authorized for this operation!");
        }
    }

    @Override
    public void approveTripRequest(TripRequest tripRequest, User user) {
        setStatus(tripRequest, user, TripRequestStatus.APPROVED);
    }

    @Override
    public void rejectTripRequest(TripRequest tripRequest, User user) {
        setStatus(tripRequest, user, TripRequestStatus.REJECTED);
    }
}
