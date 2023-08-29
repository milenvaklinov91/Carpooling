package com.telerikacademy.carpooling.controllers.mvc;

import com.telerikacademy.carpooling.controllers.AuthenticationHelper;
import com.telerikacademy.carpooling.exceptions.AuthorizationException;
import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.exceptions.UnauthorizedOperationException;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.dtos.UserFilterDto;
import com.telerikacademy.carpooling.models.filterOptions.UserFilterOptions;
import com.telerikacademy.carpooling.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserMvcController {
    private final UserServiceImpl service;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public UserMvcController(UserServiceImpl service, AuthenticationHelper authenticationHelper) {

        this.service = service;

        this.authenticationHelper = authenticationHelper;
    }
    @GetMapping("/admin")
    public String showAllUsers(@ModelAttribute("filter") UserFilterDto filter, Model model, HttpSession session) {
        UserFilterOptions filterOptions = new UserFilterOptions(
                filter.getUsername(),
                filter.getFirstName(),
                filter.getLastName(),
                filter.getSortBy(),
                filter.getSortOrder());


        User admin = authenticationHelper.tryGetCurrentUser(session);
        List<User> users = service.getAll(filterOptions, admin);
        model.addAttribute("users", users);
        model.addAttribute("filter", filter);
        return "AllUsersView";
    }
    @GetMapping("/approved")
    public String getAllApprovedUsers(Model model, HttpSession session) {
        User loggedInUser = authenticationHelper.tryGetCurrentUser(session);

        try {
            List<User> approvedUsers =service.getAllApprovedUsers(loggedInUser);
            model.addAttribute("approvedUsers", approvedUsers);
            return "approved-users";
        } catch (UnauthorizedOperationException e) {
            return "AccessDeniedView";
        }
    }

    @GetMapping("/{id}")
    public String showSingleUser(@PathVariable int id, Model model) {
        try {
            User user = service.getById(id);
            model.addAttribute("user", user);
            return "UserProfile";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        }
    }


    @GetMapping("/{id}/block")
    public String blockUser(@PathVariable int id, Model model, HttpSession session) {
        try {
            User admin = authenticationHelper.tryGetCurrentUser(session);
            if (!admin.isAdmin()) {
                throw new AuthorizationException("Only admins can make this operation!");
            }
            service.blockUser(id, admin);
            return "redirect:/users/admin";
        } catch (AuthorizationException e) {
            return "redirect:/auth/login";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }
    }

    @GetMapping("/{id}/unblock")
    public String unblockUser(@PathVariable int id, Model model, HttpSession session) {
        try {
            User admin = authenticationHelper.tryGetCurrentUser(session);
            if (!admin.isAdmin()) {
                throw new AuthorizationException("Only admins can make this operation!");
            }

            service.unBlockUser(id, admin);
            return "redirect:/users/admin";
        } catch (AuthorizationException e) {
            return "redirect:/auth/login";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }
    }

    @GetMapping("/{id}/make-admin")
    public String makeAdmin(@PathVariable int id, Model model, HttpSession session) {
        try {
            User admin = authenticationHelper.tryGetCurrentUser(session);
            if (!admin.isAdmin()) {
                throw new AuthorizationException("Only admins can make this operation!");
            }
            service.makeAdmin(id, admin);
            return "redirect:/users";
        } catch (AuthorizationException e) {
            return "redirect:/auth/login";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }
    }

    @GetMapping("/{id}/demote-admin")
    public String demoteAdmin(@PathVariable int id, Model model, HttpSession session) {
        try {
            User admin = authenticationHelper.tryGetCurrentUser(session);
            if (!admin.isAdmin()) {
                throw new AuthorizationException("Only admins can make this operation!");
            }
            service.demoteAdmin(id, admin);
            return "redirect:/users";
        } catch (AuthorizationException e) {
            return "redirect:/auth/login";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }
    }

}
