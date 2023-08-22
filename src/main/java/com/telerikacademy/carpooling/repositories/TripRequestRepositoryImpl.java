package com.telerikacademy.carpooling.repositories;

import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.models.TripRequest;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.repositories.interfaces.TripRequestRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TripRequestRepositoryImpl implements TripRequestRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public TripRequestRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public TripRequest getTripRequestById(int id) {
        try (Session session = sessionFactory.openSession()) {
            TripRequest tripRequest = session.get(TripRequest.class, id);
            if (tripRequest == null) {
                throw new EntityNotFoundException("TripRequest", id);
            }
            return tripRequest;
        }
    }

    @Override
    public List<TripRequest> getAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<TripRequest> query = session.createQuery("from TripRequest ", TripRequest.class);
            return query.list();
        }
    }

    @Override
    public void create(TripRequest tripRequest) {
        try (Session session = sessionFactory.openSession()) {

            session.save(tripRequest);
        }
    }

    @Override
    public void modify(TripRequest tripRequest) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(tripRequest);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(int id) {
        TripRequest tripRequestToDelete = getTripRequestById(id);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(tripRequestToDelete);
            session.getTransaction().commit();
        }
    }
}
