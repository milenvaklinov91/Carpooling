package com.telerikacademy.carpooling.repositories;

import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.models.Car;
import com.telerikacademy.carpooling.repositories.interfaces.CarRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CarRepositoryImpl implements CarRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public CarRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Car car) {
        try (Session session = sessionFactory.openSession()) {
            session.save(car);
        }
    }

    @Override
    public void update(Car car) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(car);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(int id) {
        Car carToDelete = getCarById(id);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(carToDelete);
            session.getTransaction().commit();
        }
    }

    public Car getCarById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Car car = session.get(Car.class, id);
            if (car == null) {
                throw new EntityNotFoundException("Car", id);
            }
            return car;
        }
    }


}
