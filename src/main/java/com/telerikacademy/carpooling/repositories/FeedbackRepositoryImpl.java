package com.telerikacademy.carpooling.repositories;

import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.models.Feedback;
import com.telerikacademy.carpooling.models.FeedbackComment;
import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.filterOptions.FeedbackFilterOptions;
import com.telerikacademy.carpooling.repositories.interfaces.FeedbackRepository;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class FeedbackRepositoryImpl implements FeedbackRepository {

    private final SessionFactory sessionFactory;

    public FeedbackRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Feedback> getAll(FeedbackFilterOptions feedbackFilterOptions) {
        try (Session session = sessionFactory.openSession()) {
            StringBuilder hqlBuilder = new StringBuilder("SELECT DISTINCT f FROM Feedback f");
            List<String> filters = new ArrayList<>();
            Map<String, Object> params = new HashMap<>();
            feedbackFilterOptions.getRatingValue().ifPresent(value -> {
                filters.add("f.ratingValue = :ratingValue");
                params.put("ratingValue", value);
            });
            if (!filters.isEmpty()) {
                hqlBuilder.append(" WHERE ").append(String.join(" AND ", filters));
            }
            hqlBuilder.append(generateOrderBy(feedbackFilterOptions));

            Query<Feedback> query = session.createQuery(hqlBuilder.toString(), Feedback.class);
            query.setProperties(params);
            return query.list();
        }
    }

    public String generateOrderBy(FeedbackFilterOptions filterOptions) {
        if (filterOptions.getSortBy().isEmpty()) {
            return "";
        }
        String orderBy = "";
        switch (filterOptions.getSortBy().get()) {
            case "ratingValue":
                orderBy = "ratingValue";
                break;
            default:
                throw new IllegalArgumentException("Invalid sorting criteria.");
        }
        orderBy = String.format(" ORDER BY %s ", orderBy);
        if (filterOptions.getSortOrder().isPresent() && filterOptions.
                getSortOrder().get().equalsIgnoreCase("desc")) {
            orderBy = String.format(" %s desc ", orderBy);
        }
        return orderBy;
    }
    @Override
    public List<User> getTopRatedUsers() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery(
                    "SELECT f.ratedUser FROM Feedback f " + "WHERE f.ratedUser.isDriver = true " +
                            "GROUP BY f.ratedUser " +
                            "ORDER BY AVG(f.ratingValue) DESC", User.class);
            query.setMaxResults(10);
            List<User> result = query.getResultList();
            if (result.isEmpty()) {
                throw new EntityNotFoundException("No top-rated drivers found.");
            }
            return result;
        }
    }

    @Override
    public List<User> getTopRatedPassengers() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery(
                    "SELECT f.ratedUser FROM Feedback f " + "WHERE f.ratedUser.isDriver = false " +
                            "GROUP BY f.ratedUser " +
                            "ORDER BY AVG(f.ratingValue) DESC", User.class);
            query.setMaxResults(10);
            List<User> result = query.getResultList();
            if (result.isEmpty()) {
                throw new EntityNotFoundException("No top-rated passengers found.");
            }
            return result;
        }
    }
    @Override
    public Feedback getFeedbackById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Feedback feedback = session.get(Feedback.class, id);
            if (feedback == null) {
                throw new EntityNotFoundException("Feedback", id);
            }
            return feedback;
        }
    }


    public List<Feedback> findAllFeedbacksByUser(int userId) {
        try (Session session = sessionFactory.openSession()) {
            org.hibernate.query.Query query = session.createQuery(
                    "From Feedback WHERE ratedUser.id= :user_id", Feedback.class);
            query.setParameter("user_id", userId);
            List<Feedback> allFeedbacks = query.list();
            if (allFeedbacks.size() == 0) {
                throw new EntityNotFoundException("This passenger doesn't have travels yet!");
            }
            return allFeedbacks;
        }
    }

    public List<Integer> getRatingValuesForUser(int userId) {
        List<Feedback> feedbackList = findAllFeedbacksByUser(userId);

        if (feedbackList.isEmpty()) {
            return Collections.emptyList();
        }

        return feedbackList.stream()
                .map(Feedback::getRatingValue)
                .collect(Collectors.toList());
    }

    public double getAverageRatingValueForUser(int userId) {
        List<Feedback> feedbackList = findAllFeedbacksByUser(userId);

        if (feedbackList.isEmpty()) {
            return 0.0;
        }

        double sum = feedbackList.stream()
                .mapToDouble(Feedback::getRatingValue)
                .sum();

        double average = sum / feedbackList.size();
        BigDecimal roundedAverage = BigDecimal.valueOf(average).setScale(1, RoundingMode.HALF_UP);

        return roundedAverage.doubleValue();
    }

    @Override
    public void create(Feedback feedback) {
        try (Session session = sessionFactory.openSession()) {
            session.save(feedback);
        }
    }

    @Override
    public void delete(int id) {
        Feedback feedbackToBeDeleted = getFeedbackById(id);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(feedbackToBeDeleted);
            session.getTransaction().commit();
        }
    }


    @Override
    public boolean hasUserRatedAnotherUser(int userId, int ratedUserId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(f) FROM Feedback f " +
                            "WHERE f.userByCreatedBy.id = :userId " +
                            "AND f.ratedUser.id = :ratedUserId",
                    Long.class
            );
            query.setParameter("userId", userId);
            query.setParameter("ratedUserId", ratedUserId);

            Long result = query.getSingleResult();
            return result > 0;
        }
    }

}