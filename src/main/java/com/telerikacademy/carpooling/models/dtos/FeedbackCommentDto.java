package com.telerikacademy.carpooling.models.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class FeedbackCommentDto {
    @NotNull(message = "Content can't be empty")
    @Size(min = 32, max = 8192, message = "Content should be between 32 and 8192 symbols")
    private String feedbackComment;

    private int FeedbackId;

    public FeedbackCommentDto() {
    }

    public String getFeedbackComment() {
        return feedbackComment;
    }

    public void setFeedbackComment(String feedbackComment) {
        this.feedbackComment = feedbackComment;
    }

    public int getFeedbackId() {
        return FeedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        FeedbackId = feedbackId;
    }
}
