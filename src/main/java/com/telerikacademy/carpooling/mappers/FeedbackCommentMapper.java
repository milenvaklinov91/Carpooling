package com.telerikacademy.carpooling.mappers;

import com.telerikacademy.carpooling.models.FeedbackComment;
import com.telerikacademy.carpooling.models.dtos.FeedbackCommentDto;
import com.telerikacademy.carpooling.services.interfaces.FeedbackCommentService;
import com.telerikacademy.carpooling.services.interfaces.FeedbackService;
import org.springframework.stereotype.Component;

@Component
public class FeedbackCommentMapper {

    private final FeedbackCommentService feedbackCommentService;
    private final FeedbackService feedbackService;

    public FeedbackCommentMapper(FeedbackCommentService feedbackCommentService, FeedbackService feedbackService) {
        this.feedbackCommentService = feedbackCommentService;
        this.feedbackService = feedbackService;
    }

    public FeedbackComment fromFeedbackCommentDto(FeedbackCommentDto feedbackCommentDto){
        FeedbackComment feedbackComment = new FeedbackComment();
        feedbackComment.setFeedback(feedbackService.getFeedbackById(feedbackCommentDto.getFeedbackId()));
        feedbackComment.setComment(feedbackCommentDto.getFeedbackComment());
        return feedbackComment;
    }
    public FeedbackComment fromFeedbackCommentDtoWithId(FeedbackCommentDto feedbackCommentDto, int id){
        FeedbackComment feedbackComment = fromFeedbackCommentDto(feedbackCommentDto);
        feedbackComment.setFeedbackCommentId(id);
        FeedbackComment repositoryFeedbackComment = feedbackCommentService.getFeedbackCommentById(id);
        feedbackComment.setUserCreatedBy(repositoryFeedbackComment.getUserCreatedBy());
        return feedbackComment;
    }
}
