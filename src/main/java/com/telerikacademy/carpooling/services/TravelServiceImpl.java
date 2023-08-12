package com.telerikacademy.carpooling.services;

import com.telerikacademy.carpooling.exceptions.UnauthorizedOperationException;
import com.telerikacademy.carpooling.models.Travel;
import com.telerikacademy.carpooling.models.User;
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
    public Travel getTravelById(int id) {
        return travelRepository.getTravelById(id);
    }
    @Override
    public List<Travel> getAll() {
        return travelRepository.getAll();
    }

    @Override
    public void create(Travel travel, User user) {


    }

    @Override
    public void modify(Travel travel, User user) {
        /*if (user.isBlocked()) {
            throw new UnauthorizedOperationException("You`re blocked!!!");
        } else if (!(post.getCreatedBy().getUsername().equals(user.getUsername()))) {
            throw new UnauthorizedOperationException("You're not authorized for this operation");
        }*/
        travelRepository.modify(travel);
    }

    @Override
    public void delete(int id, User user) {
        Travel travel = travelRepository.getTravelById(id);
        /*if (user.isBlocked()) {
            throw new UnauthorizedOperationException("You`re blocked!!!");
        } else if (!(user.isAdmin() || travel.getCreatedBy().getUsername().equals(user.getUsername()))) {
            throw new UnauthorizedOperationException("You're not authorized for this operation");
        }*/
        travelRepository.delete(id);
    }


}