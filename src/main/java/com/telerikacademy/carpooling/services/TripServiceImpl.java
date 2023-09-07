package com.telerikacademy.carpooling.services;

import com.telerikacademy.carpooling.exceptions.UnauthorizedOperationException;
import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.enums.TripStatus;
import com.telerikacademy.carpooling.models.filterOptions.TripFilterOptions;
import com.telerikacademy.carpooling.repositories.TripRepositoryImpl;
import com.telerikacademy.carpooling.services.interfaces.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripServiceImpl implements TripService {

    private TripRepositoryImpl tripRepository;

    @Autowired
    public TripServiceImpl(TripRepositoryImpl tripRepository) {
        this.tripRepository = tripRepository;
    }

    @Override
    public Trip getTripById(int id) {
        return tripRepository.getTripById(id);
    }

    @Override
    public List<Trip> getAll(TripFilterOptions tripFilterOptions) {
        return tripRepository.getAll(tripFilterOptions);
    }

    @Override
    public List<Trip> getAllCompletedTrips() {
        return tripRepository.getAllCompletedTrips();
    }

    @Override
    public Long countAllCompletedTrips() {
        return tripRepository.countAllCompletedTrips();
    }

    @Override
    public void create(Trip trip, User user) {
        trip.setCreatedBy(user);
        if (!trip.getCreatedBy().isDriver()) {
            throw new UnauthorizedOperationException("You're not authorized for this operation!");
        } else if (trip.getCreatedBy().isBlocked()) {
            throw new UnauthorizedOperationException("You`re blocked!!!");
        }
        tripRepository.create(trip);
    }

    @Override
    public void modify(Trip trip, User user) {
        if (user.isBlocked()) {
            throw new UnauthorizedOperationException("You`re blocked!!!");
        } else if (!(trip.getCreatedBy().getUsername().equals(user.getUsername()))) {
            throw new UnauthorizedOperationException("You're not authorized for this operation!");
        }
        tripRepository.modify(trip);
    }

    @Override
    public void delete(int id, User user) {
        Trip trip = tripRepository.getTripById(id);
        if (user.isBlocked()) {
            throw new UnauthorizedOperationException("You`re blocked!!!");
        } else if (!(user.isAdmin() || trip.getCreatedBy().getUsername().equals(user.getUsername()))) {
            throw new UnauthorizedOperationException("You're not authorized for this operation");
        }
        tripRepository.delete(id);
    }

    public void inProgressTripStatus(Trip trip, User user) {
        if (trip.getCreatedBy().equals(user)) {
            trip.setTripStatus(TripStatus.INPROGRESS);
            tripRepository.modify(trip);
        } else {
            throw new UnauthorizedOperationException("You're not authorized for this operation!");
        }
    }

    public void finishedTripStatus(Trip trip, User user) {
        if (trip.getCreatedBy().equals(user)) {
            trip.setTripStatus(TripStatus.FINISHED);
            tripRepository.modify(trip);
        } else {
            throw new UnauthorizedOperationException("You're not authorized for this operation!");
        }
    }

    public Long countCompletedTripsByUser(int userId) {
        return tripRepository.countCompletedTripsByUser(userId);
    }
}
