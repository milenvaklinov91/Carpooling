package com.telerikacademy.carpooling.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

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
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userCreatedBy;

    @Column(name = "comment")
    private String comment;

    @JsonIgnore
    @OneToMany(mappedBy = "feedback", fetch = FetchType.EAGER)
    private Set<FeedbackComment> comments;
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

    public User getUserCreatedBy() {
        return userCreatedBy;
    }

    public void setUserCreatedBy(User userCreatedBy) {
        this.userCreatedBy = userCreatedBy;
    }

    public Set<FeedbackComment> getComments() {
        return comments;
    }

    public void setComments(Set<FeedbackComment> comments) {
        this.comments = comments;
    }
}
