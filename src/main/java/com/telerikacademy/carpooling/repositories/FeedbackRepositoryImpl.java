package com.telerikacademy.carpooling.repositories;

import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.models.Feedback;
import com.telerikacademy.carpooling.models.FeedbackComment;
import com.telerikacademy.carpooling.repositories.interfaces.FeedbackRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;
@Repository
public class FeedbackRepositoryImpl implements FeedbackRepository {

    private final SessionFactory sessionFactory;

    public FeedbackRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Feedback getFeedbackById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Feedback feedback = session.get(Feedback.class, id);
            if (feedback == null) {
                throw new EntityNotFoundException("Feedback", id);
            }
            return feedback;
        }
    }

    @Override
    public List<Feedback> getRatingByUser(int userId) {
        try (Session session = sessionFactory.openSession()) {
            TypedQuery<Feedback> query = session.createQuery(
                    "SELECT r FROM Feedback r WHERE r.ratedUser.id = :userId", Feedback.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        }
    }

    @Override
    public void create(Feedback feedback) {
        try (Session session = sessionFactory.openSession()) {
            session.save(feedback);
        }
    }
}
