package com.telerikacademy.carpooling.controllers.mvc;

import com.telerikacademy.carpooling.controllers.AuthenticationHelper;
import com.telerikacademy.carpooling.exceptions.*;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.UUID;

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

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
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
            return "userLogin";
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
                                 BindingResult bindingResult,
                                 @RequestParam("profilePicture") MultipartFile profilePicture) {
        if (bindingResult.hasErrors()) {
            return "userRegister";
        }

        if (!register.getPassword().equals(register.getPassword())) {
            bindingResult.rejectValue("passwordConfirm", "password_error",
                    "Password confirmation should match password.");
            return "userRegister";
        }

        try {
            if (!profilePicture.isEmpty()) {
                String profilePicFilename = saveProfilePicture(profilePicture);
                register.setProfilePic(profilePicFilename);
            }

            User user = userMapper.fromDto(register);
            userService.create(user);
            emailService.sendConfirmationEmail(user.getEmail(), user.getConfirmationCode());
            return "confirm";
        } catch (EntityNotFoundException | EntityDuplicateException e) {
            bindingResult.rejectValue("username", "username_error", e.getMessage());
            return "userRegister";
        } catch (EmailExitsException e) {
            bindingResult.rejectValue("email", "email", e.getMessage());
            return "userRegister";
        } catch (InvalidPasswordException e) {
            bindingResult.rejectValue("Password", "password", e.getMessage());
            return "userRegister";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String saveProfilePicture(MultipartFile profilePicture) throws IOException {
        String uploadDir = "D:\\TelerikAcademy Proects JAva\\carpooling\\src\\main\\resources\\static\\images\\profile_pics";
        String uniqueFilename = UUID.randomUUID().toString() + "_" + profilePicture.getOriginalFilename();
        String filePath = Paths.get(uploadDir, uniqueFilename).toString();

        try (OutputStream os = new FileOutputStream(filePath)) {
            os.write(profilePicture.getBytes());
        } catch (IOException e) {
            throw new IOException("Could not save profile picture: " + e.getMessage());
        }

        return uniqueFilename;
    }

    @GetMapping("/confirm")
    public String showConfirmationPage(Model model) {
        model.addAttribute("confirm");
        return "confirm"; //
    }

    @PostMapping("/confirm")
    public RedirectView confirmUser(@Valid @ModelAttribute("confirm") @RequestParam String email,
                                    @RequestParam String confirmationCode, Model model) {
        model.addAttribute("confirm");
        model.addAttribute("email");
        model.addAttribute("confirmationCode");

        User user = userService.getByEmail(email);
        if (user != null && user.getConfirmationCode().equals(confirmationCode)) {
            user.setStatus(2);
            userService.update(user,user);
            model.addAttribute("message", "User confirmed successfully.");
            return new RedirectView("/auth/login");
        } else {
            model.addAttribute("error", "Invalid confirmation code or email.");
            return new RedirectView("/auth/confirm");
        }
    }

}