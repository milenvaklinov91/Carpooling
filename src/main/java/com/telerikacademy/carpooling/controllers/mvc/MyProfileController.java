package com.telerikacademy.carpooling.controllers.mvc;

import com.telerikacademy.carpooling.controllers.AuthenticationHelper;
import com.telerikacademy.carpooling.exceptions.AuthorizationException;
import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/my-profile")
public class MyProfileController {
    private final AuthenticationHelper authenticationHelper;
    private final UserServiceImpl service;

    @Autowired
    public MyProfileController(AuthenticationHelper authenticationHelper, UserServiceImpl service) {
        this.authenticationHelper = authenticationHelper;
        this.service = service;
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {

        return session.getAttribute("currentUser") != null;
    }

    @GetMapping
    public String showMyProfile( Model model, HttpSession session) {
        try {
            User currentUser = authenticationHelper.tryGetCurrentUser(session);
            model.addAttribute("user", currentUser);
            return "myProfileView";
        } catch (AuthorizationException e) {
            model.addAttribute("error", "You don't have access to the requested resource.");
            return "AccessDeniedView";
        }
    }

    @PostMapping()
    public String updateProfile(@ModelAttribute("user") User updatedUser, HttpSession session, Model model) {
        try {
            User loggedInUser = authenticationHelper.tryGetCurrentUser(session);
            User existingUser = service.getById(loggedInUser.getId());
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPhone_number(updatedUser.getPhone_number());
            existingUser.setProfilePic(updatedUser.getProfilePic());
            service.update( existingUser,loggedInUser);
            return "redirect:/my-profile";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        }
    }
}

