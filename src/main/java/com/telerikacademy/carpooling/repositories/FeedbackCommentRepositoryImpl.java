package com.telerikacademy.carpooling.repositories;

import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.models.Feedback;
import com.telerikacademy.carpooling.models.FeedbackComment;
import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.repositories.interfaces.FeedbackCommentRepository;
import com.telerikacademy.carpooling.repositories.interfaces.FeedbackRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

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
                throw new EntityNotFoundException("Feedback", id);
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

    @Override
    public void modify(FeedbackComment feedbackComment) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(feedbackComment);
            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteComment(int id) {
        FeedbackComment feedbackToDelete = getFeedbackCommentById(id);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(feedbackToDelete);
            session.getTransaction().commit();
        }
    }

    public List<FeedbackComment> findFeedbackCommentsByFeedbackId(int feedbackId) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery(
                    "FROM FeedbackComment WHERE feedback.id = :feedback_id", FeedbackComment.class);
            query.setParameter("feedback_id", feedbackId);
            List<FeedbackComment> feedbackComments = query.getResultList();
            if (feedbackComments.isEmpty()) {
                throw new EntityNotFoundException("No comments found for this feedback.");
            }
            return feedbackComments;
        }
    }


}
