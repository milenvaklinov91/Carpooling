package com.telerikacademy.carpooling.repositories;

import com.telerikacademy.carpooling.models.Rating;
import com.telerikacademy.carpooling.repositories.interfaces.RatingRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;
@Repository
public class RatingRepositoryImpl implements RatingRepository {

    private final SessionFactory sessionFactory;

    public RatingRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Rating> getRatingByUser(int userId) {
        try (Session session = sessionFactory.openSession()) {
            TypedQuery<Rating> query = session.createQuery(
                    "SELECT r FROM Rating r WHERE r.ratedUser.id = :userId", Rating.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        }
    }

    @Override
    public void create(Rating rating) {
        try (Session session = sessionFactory.openSession()) {
            session.save(rating);
        }
    }
}
