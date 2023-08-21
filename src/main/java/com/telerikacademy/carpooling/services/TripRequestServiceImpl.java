package com.telerikacademy.carpooling.services;

import com.telerikacademy.carpooling.exceptions.SeatException;
import com.telerikacademy.carpooling.exceptions.UnauthorizedOperationException;
import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.models.TripRequest;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.enums.TripRequestStatus;
import com.telerikacademy.carpooling.repositories.TripRepositoryImpl;
import com.telerikacademy.carpooling.repositories.interfaces.TripRequestRepository;
import com.telerikacademy.carpooling.services.interfaces.TripRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripRequestServiceImpl implements TripRequestService {

    private TripRequestRepository tripRequestRepository;
    private TripRepositoryImpl tripRepository;

    @Autowired
    public TripRequestServiceImpl(TripRequestRepository tripRequestRepository, TripRepositoryImpl tripRepository) {
        this.tripRequestRepository = tripRequestRepository;
        this.tripRepository = tripRepository;

    }

    @Override
    public TripRequest getTripRequestById(int id) {
        return tripRequestRepository.getTripRequestById(id);
    }

    @Override
    public List<TripRequest> getAll() {
        return tripRequestRepository.getAll();
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
        reduceAvailableSeats(tripRequest.getTrip());
        setStatus(tripRequest, user, TripRequestStatus.APPROVED);
    }

    @Override
    public void rejectTripRequest(TripRequest tripRequest, User user) {
        if (tripRequest.getTripRequestStatus() == TripRequestStatus.APPROVED) {
            Trip trip = tripRequest.getTrip();
            increaseAvailableSeats(trip);
        }
        setStatus(tripRequest, user, TripRequestStatus.REJECTED);
    }

    private void reduceAvailableSeats(Trip trip) {
        int currentAvailableSeats = trip.getAvailableSeats();
        if (currentAvailableSeats > 0) {
            trip.setAvailableSeats(currentAvailableSeats - 1);
            tripRepository.modify(trip);
        } else {
            throw new SeatException("No available seats left.");

        }
    }

    public void increaseAvailableSeats(Trip trip) {
        int currentAvailableSeats = trip.getAvailableSeats();

        if (currentAvailableSeats < trip.getCreatedBy().getCar().getCapacity()) {
            trip.setAvailableSeats(currentAvailableSeats + 1);
            tripRepository.modify(trip);
        } else {
            throw new SeatException("Maximum capacity reached.");
        }
    }

}
