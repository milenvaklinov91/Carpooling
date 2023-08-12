package com.telerikacademy.carpooling.repositories;

import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.models.Travel;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.repositories.interfaces.TravelRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class TravelRepositoryImpl implements TravelRepository {
    private final SessionFactory sessionFactory;
    @Autowired
    public TravelRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Override
    public Travel getTravelById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Travel travel = session.get(Travel.class, id);
            if (travel == null) {
                throw new EntityNotFoundException("Travel", id);
            }
            return travel;
        }
    }
    @Override
    public List<Travel> getAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Travel> query = session.createQuery("from Travel ", Travel.class);
            return query.list();
        }
    }
    @Override
    public void create(Travel travel) {
        try (Session session = sessionFactory.openSession()) {
            /*session.beginTransaction();*/
            session.save(travel);
            /*session.getTransaction().commit();*/
        }
    }

    @Override
    public void modify(Travel travel) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(travel);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(int id) {
        Travel travelToDelete = getTravelById(id);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(travelToDelete);
            session.getTransaction().commit();
        }
    }


}
