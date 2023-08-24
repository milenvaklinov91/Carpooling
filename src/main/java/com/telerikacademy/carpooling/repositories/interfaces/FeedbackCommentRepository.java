package com.telerikacademy.carpooling.repositories.interfaces;

import com.telerikacademy.carpooling.models.FeedbackComment;

import java.util.List;

public interface FeedbackCommentRepository {
    FeedbackComment getFeedbackCommentById(int id);
    List<FeedbackComment> findFeedbackCommentsByFeedbackId(int feedbackId);
    void create(FeedbackComment feedbackComment);
    void modify(FeedbackComment feedbackComment);
    void delete(int id);
}
