package com.telerikacademy.carpooling.repositories;

import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.models.Travel;
import com.telerikacademy.carpooling.models.filterOptions.TravelFilterOptions;
import com.telerikacademy.carpooling.repositories.interfaces.TravelRepository;
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
public class TravelRepositoryImpl implements TravelRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public TravelRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Travel getTravelById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Travel travel = session.get(Travel.class, id);
            if (travel == null) {
                throw new EntityNotFoundException("Travel", id);
            }
            return travel;
        }
    }

    @Override
    public List<Travel> getAll(TravelFilterOptions travelFilterOptions) {
        try (Session session = sessionFactory.openSession()) {
            StringBuilder hqlBuilder = new StringBuilder("SELECT DISTINCT p FROM Travel p");
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

            Query<Travel> query = session.createQuery(hqlBuilder.toString(), Travel.class);
            query.setProperties(params);
            return query.list();
        }
    }

    @Override
    public void create(Travel travel) {
        try (Session session = sessionFactory.openSession()) {
            /*session.beginTransaction();*/
            session.save(travel);
            /*session.getTransaction().commit();*/
        }
    }

    @Override
    public void modify(Travel travel) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(travel);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(int id) {
        Travel travelToDelete = getTravelById(id);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(travelToDelete);
            session.getTransaction().commit();
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
