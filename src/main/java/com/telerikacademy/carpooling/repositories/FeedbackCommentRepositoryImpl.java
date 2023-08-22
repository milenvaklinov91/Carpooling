package com.telerikacademy.carpooling.repositories;

import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.models.FeedbackComment;
import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.repositories.interfaces.FeedbackCommentRepository;
import com.telerikacademy.carpooling.repositories.interfaces.FeedbackRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FeedbackCommentRepositoryImpl implements FeedbackCommentRepository {

    private SessionFactory sessionFactory;
    @Autowired
    public FeedbackCommentRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public FeedbackComment getFeedbackCommentById(int id) {
        try (Session session = sessionFactory.openSession()) {
            FeedbackComment feedbackComment = session.get(FeedbackComment.class, id);
            if (feedbackComment == null) {
                throw new EntityNotFoundException("Travel", id);
            }
            return feedbackComment;
        }
    }

    @Override
    public void create(FeedbackComment feedbackComment) {
        try (Session session = sessionFactory.openSession()) {
            session.save(feedbackComment);

        }
    }

}
