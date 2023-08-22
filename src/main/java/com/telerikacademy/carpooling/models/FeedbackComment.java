package com.telerikacademy.carpooling.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "feedback_comments")
public class FeedbackComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_comment_id")
    private int feedbackCommentId;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "feedback_id")
    private Feedback feedback;
    @Column(name = "comment")
    private String comment;

    public FeedbackComment() {
    }

    public int getFeedbackCommentId() {
        return feedbackCommentId;
    }

    public void setFeedbackCommentId(int feedbackCommentId) {
        this.feedbackCommentId = feedbackCommentId;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
