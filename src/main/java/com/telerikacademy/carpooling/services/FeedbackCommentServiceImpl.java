package com.telerikacademy.carpooling.services;

import com.telerikacademy.carpooling.models.Feedback;
import com.telerikacademy.carpooling.models.FeedbackComment;
import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.repositories.interfaces.FeedbackCommentRepository;
import com.telerikacademy.carpooling.repositories.interfaces.TripRepository;
import com.telerikacademy.carpooling.services.interfaces.FeedbackCommentService;
import com.telerikacademy.carpooling.services.interfaces.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackCommentServiceImpl implements FeedbackCommentService {

    private FeedbackCommentRepository feedbackCommentRepository;

    private TripRepository tripRepository;
    private TripService tripService;


    @Autowired
    public FeedbackCommentServiceImpl(FeedbackCommentRepository feedbackCommentRepository,
                                      TripRepository tripRepository, TripService tripService) {
        this.feedbackCommentRepository = feedbackCommentRepository;
        this.tripRepository = tripRepository;
        this.tripService = tripService;
    }

    @Override
    public FeedbackComment getFeedbackCommentById(int id) {
        return feedbackCommentRepository.getFeedbackCommentById(id);
    }

    public void addCommentToFeedback(Feedback feedback, String commentText) {
        FeedbackComment feedbackComment = new FeedbackComment();
        /*feedbackComment.setFeedbackId(feedback.getFeedbackId());*/
        feedbackComment.setComment(commentText);
        feedbackCommentRepository.create(feedbackComment);
    } //todo да се провери дали човека който добавя коментар е пасажер!!! И е шофьор!

        @Override
    public void create(FeedbackComment feedbackComment,User user) {
        feedbackCommentRepository.create(feedbackComment);
    }



}
