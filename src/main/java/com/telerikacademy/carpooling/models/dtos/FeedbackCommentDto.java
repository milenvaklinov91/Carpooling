package com.telerikacademy.carpooling.models.dtos;

import javax.validation.constraints.NotBlank;

public class FeedbackCommentDto {
    @NotBlank
    private String feedbackComment;

    public FeedbackCommentDto() {
    }

    public String getFeedbackComment() {
        return feedbackComment;
    }

    public void setFeedbackComment(String feedbackComment) {
        this.feedbackComment = feedbackComment;
    }
}
