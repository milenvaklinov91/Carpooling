package com.telerikacademy.carpooling.services;

import com.telerikacademy.carpooling.exceptions.UnauthorizedOperationException;
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

import java.util.List;

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

    @Override
    public List<FeedbackComment> findFeedbackCommentsByFeedbackId(int feedbackId) {
        return feedbackCommentRepository.findFeedbackCommentsByFeedbackId(feedbackId);
    }


        @Override
    public void create(FeedbackComment feedbackComment,User user) {
            feedbackComment.setUserCreatedBy(user);
            if (feedbackComment.getUserCreatedBy().isBlocked()) {
                throw new UnauthorizedOperationException("You`re blocked!");
            }

            feedbackCommentRepository.create(feedbackComment);
    }

    @Override
    public void modify(FeedbackComment feedbackComment, User user) {
        if (!(feedbackComment.getUserCreatedBy().getUsername().equals(user.getUsername()))) {
            throw new UnauthorizedOperationException("You're not authorized to perform this operation as your are not the owner of this comment");
        }
        else if(feedbackComment.getUserCreatedBy().isBlocked()){
            throw new UnauthorizedOperationException("You`re blocked!");
        }
        feedbackCommentRepository.modify(feedbackComment);
    }

    @Override
    public void deleteComment(int id, User user) {
        FeedbackComment feedbackComment = feedbackCommentRepository.getFeedbackCommentById(id);
        if (feedbackComment.getUserCreatedBy().isBlocked()) {
            throw new UnauthorizedOperationException("You`re blocked!!!");
        } else if (!(user.isAdmin() || feedbackComment.getUserCreatedBy().getUsername().equals(user.getUsername()))) {
            throw new UnauthorizedOperationException("You're not authorized for this operation");
        }
        feedbackCommentRepository.deleteComment(id);
    }


}
