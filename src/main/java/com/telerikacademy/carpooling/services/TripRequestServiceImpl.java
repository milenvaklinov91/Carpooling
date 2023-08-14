package com.telerikacademy.carpooling.services;

import com.telerikacademy.carpooling.exceptions.UnauthorizedOperationException;
import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.models.TripRequest;
import com.telerikacademy.carpooling.models.User;
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
    public void create(TripRequest tripRequest, Trip trip, User user) {
        tripRequest.setPassenger(user);
        if (tripRequest.getPassenger().isBlocked()) {
            throw new UnauthorizedOperationException("You`re blocked!!!");
        }
        tripRequestRepository.create(tripRequest);
    }

    @Override
    public void modify(TripRequest tripRequest, Trip trip, User user) {
        if (user.isBlocked()) {
            throw new UnauthorizedOperationException("You`re blocked!!!");
        } else if (!(tripRequest.getPassenger().getUsername().equals(user.getUsername()))) {
            throw new UnauthorizedOperationException("You're not authorized for this operation!");
        }
        tripRequestRepository.modify(tripRequest);
    }

    @Override
    public void delete(int id,User user) {
        TripRequest tripRequest = tripRequestRepository.getTripRequestById(id);
        if (user.isBlocked()) {
            throw new UnauthorizedOperationException("You`re blocked!!!");
        } else if (!(user.isAdmin() || tripRequest.getPassenger().getUsername().equals(user.getUsername()))) {
            throw new UnauthorizedOperationException("You're not authorized for this operation");
        }
        tripRequestRepository.delete(id);
    }
}
