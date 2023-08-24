package com.telerikacademy.carpooling.controllers.mvc;

import com.telerikacademy.carpooling.controllers.AuthenticationHelper;
import com.telerikacademy.carpooling.exceptions.AuthorizationException;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.services.interfaces.TripService;
import com.telerikacademy.carpooling.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class HomeMvcController {
    private final TripService tripService;
    private final UserService userService;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public HomeMvcController(TripService tripService, UserService userService,
                             AuthenticationHelper authenticationHelper) {
        this.tripService = tripService;
        this.userService = userService;
        this.authenticationHelper= authenticationHelper;
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

   /* @GetMapping
    public String showHomePage(@ModelAttribute("filter") PostFilterDto filter,Model model) {

        List<Post> topCommentedPosts = postRepository.getMostCommented();

        List<Post> latestPosts = postRepository.getLastTenCreatedPosts();

        List<Post> mostLikedPost = postRepository.getMostLiked();


        Long numberOfUsers = userService.countAllUsers();
        Long numberOfPosts = postService.countAllPosts();

        model.addAttribute("topCommentedPosts", topCommentedPosts);
        model.addAttribute("latestPosts", latestPosts);
        model.addAttribute("mostLikedPost", mostLikedPost);
        model.addAttribute("numberOfUsers", numberOfUsers);
        model.addAttribute("numberOfPosts", numberOfPosts);

        return "homePage";
    }*/

    @GetMapping("/admin")
    public String showAdminPortal(HttpSession session, Model model) {
        try {
            User user = authenticationHelper.tryGetCurrentUser(session);
            if(!user.isAdmin()) {
                model.addAttribute("error", "You don't have access to the requested resource.");
                return "AccessDeniedView";
            }
        } catch (AuthorizationException e) {
            return "redirect:/auth/login";
        }

        return "AdminPortalView";
    }
}

