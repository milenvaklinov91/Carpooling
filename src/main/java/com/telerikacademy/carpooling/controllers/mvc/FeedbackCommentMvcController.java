package com.telerikacademy.carpooling.controllers.mvc;

import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.models.FeedbackComment;
import com.telerikacademy.carpooling.services.interfaces.FeedbackCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/feedbacks")
public class FeedbackCommentMvcController {

    private final FeedbackCommentService feedbackCommentService;

    @Autowired
    public FeedbackCommentMvcController(FeedbackCommentService feedbackCommentService) {
        this.feedbackCommentService = feedbackCommentService;
    }
    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    @GetMapping("/{id}")
    public String showFeedbackComment(@PathVariable int id, Model model) {
        try{
        FeedbackComment feedbackComment = feedbackCommentService.getFeedbackCommentById(id);
            model.addAttribute("feedbackComment", feedbackComment);
            return "feedbackComment";
        }catch (EntityNotFoundException e){
            model.addAttribute("error", e.getMessage());
            return "no-feedbackComments";
        }
    }


}
