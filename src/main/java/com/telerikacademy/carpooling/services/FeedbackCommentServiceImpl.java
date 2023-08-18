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

    @Autowired
    public FeedbackCommentServiceImpl(FeedbackCommentRepository feedbackCommentRepository,
                                      TripRepository tripRepository) {
        this.feedbackCommentRepository = feedbackCommentRepository;
        this.tripRepository = tripRepository;
    }

    @Override
    public FeedbackComment getFeedbackCommentById(int id) {
        return feedbackCommentRepository.getFeedbackCommentById(id);
    }

    @Override
    public void create(FeedbackComment feedbackComment,User user) {
        Trip trip = tripRepository.getTripById(feedbackComment.getFeedback().getTripId());
        /*if (trip.ge)*/
        feedbackCommentRepository.create(feedbackComment);
    }



}
