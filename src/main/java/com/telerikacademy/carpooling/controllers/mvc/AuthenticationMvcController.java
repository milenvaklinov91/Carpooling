package com.telerikacademy.carpooling.controllers.mvc;

import com.telerikacademy.carpooling.controllers.AuthenticationHelper;
import com.telerikacademy.carpooling.exceptions.AuthorizationException;
import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.mappers.UserMapper;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.dtos.LoginDto;
import com.telerikacademy.carpooling.models.dtos.UserDto;
import com.telerikacademy.carpooling.services.emailServices.EmailService;
import com.telerikacademy.carpooling.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthenticationMvcController {

    private final UserService userService;
    private final AuthenticationHelper authenticationHelper;
    private final UserMapper userMapper;
    private final EmailService emailService;

    @Autowired
    public AuthenticationMvcController(UserService userService,
                                       AuthenticationHelper authenticationHelper,
                                       UserMapper userMapper, EmailService emailService) {
        this.userService = userService;
        this.authenticationHelper = authenticationHelper;
        this.userMapper = userMapper;
        this.emailService = emailService;
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("login", new LoginDto());
        return "userLogin";
    }

    @PostMapping("/login")
    public String handleLogin(@Valid @ModelAttribute("login") LoginDto login, BindingResult bindingResult,
                              HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "userLogin";
        }

        try {
            authenticationHelper.verifyAuthentication(login.getUsername(), login.getPassword());
            session.setAttribute("currentUser", login.getUsername());
            return "redirect:/";
        } catch (AuthorizationException e) {
            bindingResult.rejectValue("username", "auth_error", e.getMessage());
            return "userLoginView";
        }
    }

    @GetMapping("/logout")
    public String handleLogout(HttpSession session) {
        session.removeAttribute("currentUser");
        return "redirect:/";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("register", new UserDto());
        return "userRegister";
    }

    @PostMapping("/register")
    public String handleRegister(@Valid @ModelAttribute("register") UserDto register,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "userRegister";
        }

        if (!register.getPassword().equals(register.getPassword())) {
            bindingResult.rejectValue("passwordConfirm", "password_error",
                    "Password confirmation should match password.");
            return "userRegister";
        }

        try {
            User user = userMapper.fromDto(register);
            emailService.sendConfirmationEmail(user.getEmail(), user.getConfirmationCode());
            userService.create(user);
            return "redirect:/auth/login";
        } catch (EntityNotFoundException e) {
            bindingResult.rejectValue("username", "username_error", e.getMessage());
            return "userRegister";
        }
    }

}
