package com.telerikacademy.carpooling.repositories;

import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.models.TripRequestStatus;
import com.telerikacademy.carpooling.repositories.interfaces.TripRequestStatusRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TripRequestStatusRepositoryImpl implements TripRequestStatusRepository {
    private final SessionFactory sessionFactory;
    @Autowired
    public TripRequestStatusRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public TripRequestStatus getTripRequestStatusId(int id) {
        try (Session session = sessionFactory.openSession()) {
            TripRequestStatus tripRequestStatus = session.get(TripRequestStatus.class, id);
            if (tripRequestStatus == null) {
                throw new EntityNotFoundException("Travel", id);
            }
            return tripRequestStatus;
        }
    }

    @Override
    public List<TripRequestStatus> getAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<TripRequestStatus> query = session.createQuery
                    ("from TripRequestStatus ", TripRequestStatus.class);
            return query.list();
        }
    }

}
