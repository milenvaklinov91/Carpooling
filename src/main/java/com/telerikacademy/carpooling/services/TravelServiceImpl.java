package com.telerikacademy.carpooling.services;

import com.telerikacademy.carpooling.exceptions.UnauthorizedOperationException;
import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.filterOptions.TravelFilterOptions;
import com.telerikacademy.carpooling.repositories.TravelRepositoryImpl;
import com.telerikacademy.carpooling.services.interfaces.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TravelServiceImpl implements TravelService {

    private TravelRepositoryImpl travelRepository;

    @Autowired
    public TravelServiceImpl(TravelRepositoryImpl travelRepository) {
        this.travelRepository = travelRepository;
    }
    @Override
    public Trip getTravelById(int id) {
        return travelRepository.getTravelById(id);
    }
    @Override
    public List<Trip> getAll(TravelFilterOptions travelFilterOptions) {
        return travelRepository.getAll(travelFilterOptions);
    }

    @Override
    public void create(Trip trip, User user) {
        trip.setCreatedBy(user);
        if (!trip.getCreatedBy().isDriver()){
            throw new UnauthorizedOperationException("You're not authorized for this operation!");
        } else if (trip.getCreatedBy().isBlocked()) {
            throw new UnauthorizedOperationException("You`re blocked!!!");
        }
        travelRepository.create(trip);
    }

    @Override
    public void modify(Trip trip, User user) {
        if (user.isBlocked()) {
            throw new UnauthorizedOperationException("You`re blocked!!!");
        } else if (!(trip.getCreatedBy().getUsername().equals(user.getUsername()))) {
            throw new UnauthorizedOperationException("You're not authorized for this operation!");
        }
        travelRepository.modify(trip);
    }

    @Override
    public void delete(int id, User user) {
        Trip trip = travelRepository.getTravelById(id);
        if (user.isBlocked()) {
            throw new UnauthorizedOperationException("You`re blocked!!!");
        } else if (!(user.isAdmin() || trip.getCreatedBy().getUsername().equals(user.getUsername()))) {
            throw new UnauthorizedOperationException("You're not authorized for this operation");
        }
        travelRepository.delete(id);
    }

}
