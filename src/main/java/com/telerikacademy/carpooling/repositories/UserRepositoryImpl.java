package com.telerikacademy.carpooling.repositories;

import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.repositories.interfaces.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private final SessionFactory sessionFactory;

    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    public List<User> getAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User", User.class);
            return query.list();
        }
    }

    public Long countAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery("select count(u) from User u where 1=1", Long.class);
            return query.getSingleResult();
        }
    }

    public User getUserById(int id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, id);
            if (user == null) {
                throw new EntityNotFoundException("User", id);
            }
            return user;
        }
    }


    public User getByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery(
                    "from User user where user.username = :username", User.class);
            query.setParameter("username", username);
            List<User> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("User", "username", username);
            }
            return result.get(0);
        }
    }


    public User getByFirstName(String firstName) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery(
                    "from User user where user.firstName = :firstName", User.class);
            query.setParameter("firstName", firstName);
            List<User> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("User", "firstName", firstName);
            }
            return result.get(0);
        }
    }

    public User getByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User where email = :email", User.class);
            query.setParameter("email", email);
            List<User> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("Email", "email", email);
            }
            return result.get(0);
        }
    }
}
