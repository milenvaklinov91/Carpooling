package com.telerikacademy.carpooling.controllers.rest;

import com.telerikacademy.carpooling.controllers.AuthenticationHelper;
import com.telerikacademy.carpooling.exceptions.AuthorizationException;
import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.mappers.FeedbackMapper;
import com.telerikacademy.carpooling.models.Feedback;
import com.telerikacademy.carpooling.models.FeedbackComment;
import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.dtos.FeedbackCommentDto;
import com.telerikacademy.carpooling.models.dtos.FeedbackDto;
import com.telerikacademy.carpooling.services.interfaces.FeedbackCommentService;
import com.telerikacademy.carpooling.services.interfaces.FeedbackService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final FeedbackMapper feedbackMapper;
    private final AuthenticationHelper authenticationHelper;
    private final FeedbackCommentService feedbackCommentService;

    public FeedbackController(FeedbackService feedbackService,
                              FeedbackMapper feedbackMapper,
                              AuthenticationHelper authenticationHelper,
                              FeedbackCommentService feedbackCommentService) {
        this.feedbackService = feedbackService;
        this.feedbackMapper = feedbackMapper;
        this.authenticationHelper = authenticationHelper;
        this.feedbackCommentService = feedbackCommentService;
    }

    @GetMapping("/{id}")
    public Feedback getFeedbackById(@PathVariable int id) {
        try {
            return feedbackService.getFeedbackById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public Feedback create(@RequestHeader HttpHeaders headers, @Valid @RequestBody FeedbackDto feedbackDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Feedback feedback = feedbackMapper.fromFeedbackDto(feedbackDto);
            feedbackService.create(feedback, user);
            return feedback;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PostMapping("/{id}/add-comments")
    public FeedbackComment addComment (@RequestHeader HttpHeaders headers,@PathVariable int id,
                           @Valid @RequestBody FeedbackCommentDto feedbackCommentDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Feedback feedback = feedbackService.getFeedbackById(id);
            FeedbackComment feedbackComment = feedbackMapper.fromFeedbackCommentDto(feedbackCommentDto,feedback);
            feedbackCommentService.create(feedbackComment,user);
            return feedbackComment;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @GetMapping("/feedback-comments/{id}")
    public FeedbackComment getFeedbackCommentById(@PathVariable int id) {
        try {
            return feedbackCommentService.getFeedbackCommentById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}