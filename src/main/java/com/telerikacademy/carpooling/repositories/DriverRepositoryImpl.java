package com.telerikacademy.carpooling.repositories;

import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.models.Driver;
import com.telerikacademy.carpooling.repositories.interfaces.DriverRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DriverRepositoryImpl implements DriverRepository {
    private final SessionFactory sessionFactory;

    public DriverRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    public List<Driver> allDrivers() {
        try (Session session = sessionFactory.openSession()) {
            Query<Driver> query = session.createQuery("from Driver", Driver.class);
            return query.list();
        }
    }
    public Driver getDriverByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            Query<Driver> query = session.createQuery(
                    "SELECT d FROM Driver d JOIN d.user_id u WHERE u.username = :username",
                    Driver.class
            );
            query.setParameter("username", username);

            Driver driver = query.uniqueResult();
            if (driver == null) {
                throw new EntityNotFoundException("Driver", "username", username);
            }
            return driver;
        }
    }
}
