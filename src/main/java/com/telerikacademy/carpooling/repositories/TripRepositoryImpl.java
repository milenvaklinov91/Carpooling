package com.telerikacademy.carpooling.repositories;

import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.models.filterOptions.TravelFilterOptions;
import com.telerikacademy.carpooling.repositories.interfaces.TripRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TripRepositoryImpl implements TripRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public TripRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Trip getTripById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Trip trip = session.get(Trip.class, id);
            if (trip == null) {
                throw new EntityNotFoundException("Travel", id);
            }
            return trip;
        }
    }

    @Override
    public List<Trip> getAll(TravelFilterOptions travelFilterOptions) {
        try (Session session = sessionFactory.openSession()) {
            StringBuilder hqlBuilder = new StringBuilder("SELECT DISTINCT p FROM Trip p");
            List<String> filters = new ArrayList<>();
            Map<String, Object> params = new HashMap<>();

            travelFilterOptions.getStartLocation().ifPresent(value -> {
                filters.add("p.startLocation LIKE :startLocation");
                params.put("startLocation", String.format("%%%s%%", value));
            });

            travelFilterOptions.getStartLocation().ifPresent(value -> {
                filters.add("p.endLocation LIKE :endLocation");
                params.put("endLocation", String.format("%%%s%%", value));
            });

            travelFilterOptions.getDepartureTime().ifPresent(value -> {
                filters.add("p.departureTime LIKE :departureTime");
                params.put("departureTime", String.format("%%%s%%", value));
            });

            travelFilterOptions.getCostPerPerson().ifPresent(value -> {
                filters.add("p.costPerPerson LIKE :costPerPerson");
                params.put("costPerPerson", String.format("%%%s%%", value));
            });

            hqlBuilder.append(generateOrderBy(travelFilterOptions));

            Query<Trip> query = session.createQuery(hqlBuilder.toString(), Trip.class);
            query.setProperties(params);
            return query.list();
        }
    }

    @Override
    public void create(Trip trip) {
        try (Session session = sessionFactory.openSession()) {

            session.save(trip);
        }
    }

    @Override
    public void modify(Trip trip) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(trip);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(int id) {
        Trip tripToDelete = getTripById(id);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(tripToDelete);
            session.getTransaction().commit();
        }
    }

    public List<Trip> findAllTravelsByUser(int userId) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery(
                    "From Trip WHERE createdBy.id= :user_id", Trip.class);
            query.setParameter("user_id", userId);
            List<Trip> allTrips = query.list();
            if (allTrips.size() == 0) {
                throw new EntityNotFoundException("This passenger don't have travels yet!");
            }
            return allTrips;
        }
    }

    private String generateOrderBy(TravelFilterOptions travelFilterOptions) {
        if (travelFilterOptions.getSortBy().isEmpty()) {
            return "";
        }

        String orderBy = "";
        switch (travelFilterOptions.getSortBy().get()) {
            case "startLocation":
                orderBy = "startLocation";
                break;
            case "endLocation":
                orderBy = "endLocation";
                break;
            case "departureTime":
                orderBy = "p.departureTime";
                break;
            case "costPerPerson":
                orderBy = "p.costPerPerson";
                break;
            default:
                orderBy = "p.travelId";
        }

        orderBy = String.format(" ORDER BY %s ", orderBy);

        if (travelFilterOptions.getSortOrder().isPresent() && travelFilterOptions.
                getSortOrder().get().equalsIgnoreCase("desc")) {
            orderBy = String.format(" %s desc ", orderBy);
        }

        return orderBy;
    }
}
