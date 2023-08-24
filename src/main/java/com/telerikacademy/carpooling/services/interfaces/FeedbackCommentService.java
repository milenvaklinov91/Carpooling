package com.telerikacademy.carpooling.services.interfaces;

import com.telerikacademy.carpooling.models.FeedbackComment;
import com.telerikacademy.carpooling.models.User;

import java.util.List;

public interface FeedbackCommentService {
    FeedbackComment getFeedbackCommentById(int id);
    List<FeedbackComment> findFeedbackCommentsByFeedbackId(int feedbackId);
    void create(FeedbackComment feedbackComment, User user);
    void modify(FeedbackComment feedbackComment, User user);
    void delete(int id, User user);
}
