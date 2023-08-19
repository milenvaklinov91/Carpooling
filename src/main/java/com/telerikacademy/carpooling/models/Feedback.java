package com.telerikacademy.carpooling.models;


import javax.persistence.*;


@Entity
@Table(name = "feedbacks")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private int feedbackId;
    @Column(name = "trip_id")
    private int tripId;
    @Column(name = "rating_value")
    private int ratingValue;
    @ManyToOne
    @JoinColumn(name = "rated_user_id")
    private User ratedUser;
    @ManyToOne
    @JoinColumn(name = "rated_by_user_id")
    private User userByCreatedBy;
    /*@JsonIgnore
    @OneToMany(mappedBy = "feedback", fetch = FetchType.EAGER)
    private List<FeedbackComment> comments;*/

    public Feedback() {
    }

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public int getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(int ratingValue) {
        this.ratingValue = ratingValue;
    }


    public User getRatedUser() {
        return ratedUser;
    }

    public void setRatedUser(User ratedUser) {
        this.ratedUser = ratedUser;
    }

    public User getUserByCreatedBy() {
        return userByCreatedBy;
    }

    public void setUserByCreatedBy(User userByCreatedBy) {
        this.userByCreatedBy = userByCreatedBy;
    }

    /*public List<FeedbackComment> getComments() {
        return comments;
    }

    public void setComments(List<FeedbackComment> comments) {
        this.comments = comments;
    }*/
}


