package com.telerikacademy.carpooling.repositories.interfaces;

import com.telerikacademy.carpooling.models.FeedbackComment;

public interface FeedbackCommentRepository {
    FeedbackComment getFeedbackCommentById(int id);

    void create(FeedbackComment feedbackComment);
}
