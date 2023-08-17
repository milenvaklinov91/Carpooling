package com.telerikacademy.carpooling.models;

import javax.persistence.*;

@Entity
@Table(name = "Feedback_comments")
public class FeedbackComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_comment_id")
    private int feedbackId;
    @ManyToOne
    @JoinColumn(name = "feedback_id")
    private Feedback feedback;
    @Column(name = "comment")
    private String comment;

    public FeedbackComment() {
    }

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
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
