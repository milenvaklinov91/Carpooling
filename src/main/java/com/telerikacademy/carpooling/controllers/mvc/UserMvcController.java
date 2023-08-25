package com.telerikacademy.carpooling.controllers.mvc;

import com.telerikacademy.carpooling.controllers.AuthenticationHelper;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.dtos.UserFilterDto;
import com.telerikacademy.carpooling.models.filterOptions.UserFilterOptions;
import com.telerikacademy.carpooling.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
