package com.telerikacademy.carpooling.services;

import com.telerikacademy.carpooling.models.Feedback;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.repositories.interfaces.FeedbackRepository;
import com.telerikacademy.carpooling.services.interfaces.FeedbackService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public Feedback getFeedbackById(int id) {
        return feedbackRepository.getFeedbackById(id);
    }

    @Override
    public List<Feedback> getRatingByUser(int userId) {
        return feedbackRepository.getRatingByUser(userId);
    }

    @Override
    public void create(Feedback feedback, User user) {
        feedback.setUserByCreatedBy(user);
        feedbackRepository.create(feedback);
    }
}
