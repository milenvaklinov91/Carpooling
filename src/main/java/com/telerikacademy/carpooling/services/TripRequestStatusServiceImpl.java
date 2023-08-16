package com.telerikacademy.carpooling.services;

import com.telerikacademy.carpooling.models.TripRequestStatus;
import com.telerikacademy.carpooling.repositories.interfaces.TripRequestStatusRepository;
import com.telerikacademy.carpooling.services.interfaces.TripRequestStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TripRequestStatusServiceImpl implements TripRequestStatusService {

    private TripRequestStatusRepository tripRequestStatusRepository;
    @Autowired
    public TripRequestStatusServiceImpl(TripRequestStatusRepository tripRequestStatusRepository) {
        this.tripRequestStatusRepository = tripRequestStatusRepository;
    }

    @Override
    public TripRequestStatus getTripRequestStatusId(int id) {
        return tripRequestStatusRepository.getTripRequestStatusId(id);
    }

    @Override
    public List<TripRequestStatus> getAll() {
        return tripRequestStatusRepository.getAll();
    }
}
