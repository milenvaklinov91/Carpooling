package com.telerikacademy.carpooling.services.interfaces;

import com.telerikacademy.carpooling.models.Feedback;
import com.telerikacademy.carpooling.models.FeedbackComment;

public interface FeedbackCommentService {
    FeedbackComment getFeedbackCommentById(int id);

    void addCommentToFeedback(Feedback feedback, String commentText);
}
