//package com.telerikacademy.carpooling.controllers.mvc;
//
//import com.telerikacademy.carpooling.controllers.AuthenticationHelper;
//import com.telerikacademy.carpooling.exceptions.AuthorizationException;
//import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
//import com.telerikacademy.carpooling.mappers.FeedbackCommentMapper;
//import com.telerikacademy.carpooling.mappers.FeedbackMapper;
//import com.telerikacademy.carpooling.models.User;
//import com.telerikacademy.carpooling.services.interfaces.*;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.http.HttpSession;
//
//@Controller
//@RequestMapping("/feedbacks")
//public class FeedbackMvcController {
//
//    private final FeedbackService feedbackService;
//    private final FeedbackMapper feedbackMapper;
//    private final AuthenticationHelper authenticationHelper;
//    private final FeedbackCommentService feedbackCommentService;
//    private final TripService tripService;
//    private final TripRequestService tripRequestService;
//    private final UserService userService;
//
//    private final FeedbackCommentMapper feedbackCommentMapper;
//
//    public FeedbackMvcController(FeedbackService feedbackService,
//                              FeedbackMapper feedbackMapper,
//                              AuthenticationHelper authenticationHelper,
//                              FeedbackCommentService feedbackCommentService, TripService tripService, TripRequestService tripRequestService, UserService userService, FeedbackCommentMapper feedbackCommentMapper) {
//        this.feedbackService = feedbackService;
//        this.feedbackMapper = feedbackMapper;
//        this.authenticationHelper = authenticationHelper;
//        this.feedbackCommentService = feedbackCommentService;
//        this.tripService = tripService;
//        this.tripRequestService = tripRequestService;
//        this.userService = userService;
//        this.feedbackCommentMapper = feedbackCommentMapper;
//    }
//
//    @ModelAttribute("isAuthenticated")
//    public boolean populateIsAuthenticated(HttpSession session) {
//        return session.getAttribute("currentUser") != null;
//    }
////todo това не знам дали е нужно някъде, но го добавям
//    @GetMapping("/{id}")
//    public String showSingleFeedback(@PathVariable int id, Model model, HttpSession session) {
//        try {
//            User user = authenticationHelper.tryGetCurrentUser(session);
//        } catch (AuthorizationException e) {
//            model.addAttribute("error", e.getMessage());
//            return "AccessDeniedView";
//        } catch (EntityNotFoundException e) {
//            model.addAttribute("error", e.getMessage());
//            return "not-found";
//        }
//        return "showSingleFeedbackView";
//    }
//
//
//
//
//
//}
