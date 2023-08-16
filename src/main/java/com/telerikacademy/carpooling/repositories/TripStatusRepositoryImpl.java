package com.telerikacademy.carpooling.repositories;

import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.models.TripStatus;
import com.telerikacademy.carpooling.repositories.interfaces.TripStatusRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TripStatusRepositoryImpl implements TripStatusRepository {
    private final SessionFactory sessionFactory;
    @Autowired
    public TripStatusRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public TripStatus getTripStatusId(int id) {
        try (Session session = sessionFactory.openSession()) {
            TripStatus tripStatus = session.get(TripStatus.class, id);
            if (tripStatus == null) {
                throw new EntityNotFoundException("Travel", id);
            }
            return tripStatus;
        }
    }

    @Override
    public List<TripStatus> getAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<TripStatus> query = session.createQuery("from TripStatus ", TripStatus.class);
            return query.list();
        }
    }

}
