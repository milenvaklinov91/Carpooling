package com.telerikacademy.carpooling.services;

import com.telerikacademy.carpooling.models.Feedback;
import com.telerikacademy.carpooling.models.FeedbackComment;
import com.telerikacademy.carpooling.repositories.interfaces.FeedbackCommentRepository;
import com.telerikacademy.carpooling.services.interfaces.FeedbackCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackCommentServiceImpl implements FeedbackCommentService {

    private FeedbackCommentRepository feedbackCommentRepository;

    @Autowired
    public FeedbackCommentServiceImpl(FeedbackCommentRepository feedbackCommentRepository) {
        this.feedbackCommentRepository = feedbackCommentRepository;
    }

    @Override
    public FeedbackComment getFeedbackCommentById(int id) {
        return feedbackCommentRepository.getFeedbackCommentById(id);
    }

    public void addCommentToFeedback(Feedback feedback, String commentText) {
        FeedbackComment feedbackComment = new FeedbackComment();
        feedbackComment.setFeedbackId(feedback.getFeedbackId());
        feedbackComment.setComment(commentText);
        feedbackCommentRepository.create(feedbackComment);
    }

}
