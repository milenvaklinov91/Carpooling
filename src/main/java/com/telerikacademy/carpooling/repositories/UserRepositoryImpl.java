package com.telerikacademy.carpooling.repositories;

import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.filterOptions.UserFilterOptions;
import com.telerikacademy.carpooling.repositories.interfaces.UserRepository;
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
public class UserRepositoryImpl implements UserRepository {
    private final SessionFactory sessionFactory;


    @Autowired
    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<User> getAll(UserFilterOptions filterOptions) {
        try (Session session = sessionFactory.openSession()) {
            StringBuilder hqlBuilder = new StringBuilder("SELECT DISTINCT u FROM User u");
            List<String> filters = new ArrayList<>();
            Map<String, Object> params = new HashMap<>();

            filterOptions.getUsername().ifPresent(value -> {
                filters.add("u.username LIKE :username");
                params.put("username", String.format("%%%s%%", value));
            });
            filterOptions.getFirstName().ifPresent(value -> {
                filters.add("u.firstName like :firstName");
                params.put("firstName", String.format("%%%s%%", value));
            });
            filterOptions.getLastName().ifPresent(value -> {
                filters.add("u.lastName like :lastName");
                params.put("lastName", String.format("%%%s%%", value));
            });
            if (!filters.isEmpty()) {
                hqlBuilder.append(" WHERE ");
                hqlBuilder.append(String.join(" AND ", filters));
            }


            hqlBuilder.append(generateOrderBy(filterOptions));

            Query<User> query = session.createQuery(hqlBuilder.toString(), User.class);
            query.setProperties(params);
            return query.list();
        }
    }
/*
    public List<User> getAllAccepted(){
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery(
                    "from User user where user.status = 2", User.class);
            List<User> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("User");
            }
            return result;
        }
    }
*/

    public Long countAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery("select count(u) from User u where 1=1", Long.class);
            return query.getSingleResult();
        }
    }

    public User getUserById(int id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, id);
            if (user == null) {
                throw new EntityNotFoundException("User", id);
            }
            return user;
        }
    }


    public User getByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery(
                    "from User user where user.username = :username", User.class);
            query.setParameter("username", username);
            List<User> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("User", "username", username);
            }
            return result.get(0);
        }
    }

    public User getByLastname(String lastName) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery(
                    "from User user where lower(user.lastName) = lower(:lastName)", User.class);
            query.setParameter("lastName", lastName);
            List<User> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("User", "lastname", lastName);
            }
            return result.get(0);
        }
    }


    public User getByFirstName(String firstName) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery(
                    "from User user where LOWER( user.firstName) =lower(:firstName)", User.class);
            query.setParameter("firstName", firstName);
            List<User> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("User", "firstName", firstName);
            }
            return result.get(0);
        }
    }

    public User getByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User where email = :email", User.class);
            query.setParameter("email", email);
            List<User> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("Email", "email", email);
            }
            return result.get(0);
        }
    }

    public User getByPhoneNumber(String phoneNumber) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User where phone_number = :phoneNumber", User.class);
            query.setParameter("phoneNumber", phoneNumber);
            List<User> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("User", "phone number", phoneNumber);
            }
            return result.get(0);
        }
    }

    public List<User> getAdmins() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User WHERE isAdmin = true", User.class);
            List<User> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("No admins");
            }
            return result;
        }
    }

    public List<User> getRegularUsers() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User WHERE isAdmin = false", User.class);
            List<User> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("No users");
            }
            return result;
        }
    }
    public  List<User> getDrivers(){
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User WHERE isDriver = true", User.class);
            List<User> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("No drivers");
            }
            return result;
        }
    }
    public User getDriverByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("SELECT u FROM User u where " +
                            "u.isDriver=true and u.username = :username",
                           User.class   );
            query.setParameter("username", username);

            User driver = query.uniqueResult();
            if (driver == null) {
                throw new EntityNotFoundException("Driver", "username", username);
            }
            return driver;
        }
    }

    public  List<User> getPassengers(){
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User WHERE isDriver = false", User.class);
            List<User> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("No passengers");
            }
            return result;
        }
    }
    public  List<User> getAllPassengersbyTripId(int id){
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery(
                    "SELECT tr.passenger FROM TripRequest tr " +
                            "WHERE tr.tripRequestStatus = '1' AND tr.trip.id = :id",
                    User.class
            );
            query.setParameter("id", id);
            List<User> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("No passengers");
            }
            return result;
        }
    }

    public void create(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }
    }

    public void update(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        }
    }

    public void delete(int id) {
        User userToDelete = getUserById(id);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(userToDelete);
            session.getTransaction().commit();
        }
    }
    private String generateOrderBy(UserFilterOptions filterOptions) {
        if (filterOptions.getSortBy().isEmpty()) {
            return "";
        }

        String orderBy = "";
        switch (filterOptions.getSortBy().get()) {
            case "username":
                orderBy = "username";
                break;
            case "firstName":
                orderBy = "firstName";
                break;
            case "lastName":
                orderBy = "lastName";
                break;
            case "userTravels":
                orderBy = "userTravels";
                break;
            default:
                orderBy = "u.user_id";
        }

        orderBy = String.format(" ORDER BY %s ", orderBy);

        if (filterOptions.getSortOrder().isPresent() && filterOptions.
                getSortOrder().get().equalsIgnoreCase("desc")) {
            orderBy = String.format(" %s desc ", orderBy);
        }

        return orderBy;
    }
}
