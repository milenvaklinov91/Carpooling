package com.telerikacademy.carpooling.services;

import com.telerikacademy.carpooling.models.TripStatus;
import com.telerikacademy.carpooling.repositories.interfaces.TripStatusRepository;
import com.telerikacademy.carpooling.services.interfaces.TripStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TripStatusServiceImpl implements TripStatusService {

    private TripStatusRepository tripStatusRepository;
    @Autowired
    public TripStatusServiceImpl(TripStatusRepository tripStatusRepository) {
        this.tripStatusRepository = tripStatusRepository;
    }

    @Override
    public TripStatus getTripStatusId(int id) {
        return tripStatusRepository.getTripStatusId(id);
    }

    @Override
    public List<TripStatus> getAll() {
        return tripStatusRepository.getAll();
    }
}
