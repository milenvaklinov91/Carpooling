package com.telerikacademy.carpooling.controllers.mvc;

import com.telerikacademy.carpooling.controllers.AuthenticationHelper;
import com.telerikacademy.carpooling.exceptions.AuthorizationException;
import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.dtos.UserFilterDto;
import com.telerikacademy.carpooling.services.interfaces.FeedbackService;
import com.telerikacademy.carpooling.services.interfaces.TripService;
import com.telerikacademy.carpooling.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeMvcController {
    private final TripService tripService;
    private final UserService userService;
    private final AuthenticationHelper authenticationHelper;
    private final FeedbackService feedbackService;

    @Autowired
    public HomeMvcController(TripService tripService, UserService userService,
                             AuthenticationHelper authenticationHelper, FeedbackService feedbackService) {
        this.tripService = tripService;
        this.userService = userService;
        this.authenticationHelper = authenticationHelper;
        this.feedbackService = feedbackService;
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {

        return session.getAttribute("currentUser") != null;
    }


    @ModelAttribute("isAdmin")
    public boolean populateIsAdmin(HttpSession session) {
        try {
            User currentUser = authenticationHelper.tryGetCurrentUser(session);
            return currentUser.isAdmin();
        } catch (AuthorizationException e) {
            return false;
        }
    }

    @GetMapping
    public String showHomePage(@ModelAttribute("filter") UserFilterDto filter, Model model) {

        List<User> topUsers = feedbackService.getTopRatedUsers();

        List<User> topPassengersUsers = feedbackService.getTopRatedPassengers();

        Long completedTrips = tripService.countAllCompletedTrips();

        Long numberOfUsers = userService.countAllUsers();

        List<Double> topUsersRatings = new ArrayList<>();

        for (User user : topUsers) {
            Double userRating = feedbackService.getAverageRatingValueForUser(user.getId());
            topUsersRatings.add(userRating);
        }

        List<Long> completedTripsCounts = new ArrayList<>();

        for (User user : topUsers) {
            int userId = user.getId();
            Long completedTripsCount = tripService.countCompletedTripsByUser(userId);
            completedTripsCounts.add(completedTripsCount);
        }

        model.addAttribute("topUsers", topUsers);
        model.addAttribute("topPassengersUsers", topPassengersUsers);
        model.addAttribute("numberOfUsers", numberOfUsers);
        model.addAttribute("completedTrips", completedTrips);
        model.addAttribute("topUsersRatings", topUsersRatings);
        model.addAttribute("completedTripsCounts", completedTripsCounts);

        return "HomePage";
    }

    @GetMapping("/admin")
    public String showAdminPortal(HttpSession session, Model model) {
        try {
            User user = authenticationHelper.tryGetCurrentUser(session);
            if (!user.isAdmin()) {
                model.addAttribute("error", "You don't have access to the requested resource.");
                return "AccessDeniedView";
            }
        } catch (AuthorizationException e) {
            return "redirect:/auth/login";
        }

        return "AdminPortalView";
    }

    @GetMapping("/about-us")
    public String showAboutUs() {
        return "aboutUs";
    }

    @GetMapping("/contact-us")
    public String showContactUs(HttpSession session) {
        return "contactUs";
    }

}

