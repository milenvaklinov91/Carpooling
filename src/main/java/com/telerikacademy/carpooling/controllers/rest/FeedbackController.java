package com.telerikacademy.carpooling.controllers.rest;

import com.telerikacademy.carpooling.controllers.AuthenticationHelper;
import com.telerikacademy.carpooling.exceptions.AuthorizationException;
import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.mappers.FeedbackCommentMapper;
import com.telerikacademy.carpooling.mappers.FeedbackMapper;
import com.telerikacademy.carpooling.models.*;
import com.telerikacademy.carpooling.models.dtos.FeedbackCommentDto;
import com.telerikacademy.carpooling.models.dtos.FeedbackDto;
import com.telerikacademy.carpooling.models.filterOptions.FeedbackFilterOptions;
import com.telerikacademy.carpooling.services.interfaces.FeedbackCommentService;
import com.telerikacademy.carpooling.services.interfaces.FeedbackService;
import com.telerikacademy.carpooling.services.interfaces.TripRequestService;
import com.telerikacademy.carpooling.services.interfaces.TripService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final FeedbackMapper feedbackMapper;
    private final AuthenticationHelper authenticationHelper;
    private final FeedbackCommentService feedbackCommentService;
    private final TripService tripService;
    private final TripRequestService tripRequestService;

    private final FeedbackCommentMapper feedbackCommentMapper;

    public FeedbackController(FeedbackService feedbackService,
                              FeedbackMapper feedbackMapper,
                              AuthenticationHelper authenticationHelper,
                              FeedbackCommentService feedbackCommentService, TripService tripService, TripRequestService tripRequestService, FeedbackCommentMapper feedbackCommentMapper) {
        this.feedbackService = feedbackService;
        this.feedbackMapper = feedbackMapper;
        this.authenticationHelper = authenticationHelper;
        this.feedbackCommentService = feedbackCommentService;
        this.tripService = tripService;
        this.tripRequestService = tripRequestService;
        this.feedbackCommentMapper = feedbackCommentMapper;
    }

    @GetMapping
    public List<Feedback> getAll(@RequestParam(required = false) Integer ratingValue,
                                 @RequestParam(required = false) String sortBy,
                                 @RequestParam(required = false) String sortOrder,
                                 @RequestParam(required = false) String topRatedDrivers,
                                 @RequestParam(required = false) String topRatedPassengers) {
        FeedbackFilterOptions feedbackFilterOptions = new FeedbackFilterOptions(ratingValue, sortBy, sortOrder,
                topRatedDrivers, topRatedPassengers);
        return feedbackService.getAll(feedbackFilterOptions);
    }


    @GetMapping("/{id}")
    public Feedback getFeedbackById(@PathVariable int id) {
        try {
            return feedbackService.getFeedbackById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

//    @GetMapping("/ratedUser/{userId}")
//    public Feedback getRatingOfUser(@PathVariable int userId) {
//        try {
//            return feedbackService.getRatingOfUser(userId);
//        } catch (EntityNotFoundException e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
//        }
//    }

//    @GetMapping("/ratedUser/{userId}/rating")
//    public int getRatingValueOfUser(@PathVariable int userId) {
//        try {
//            List<Feedback> feedbackList = feedbackService.findAllFeedbacksByUser(userId);
//            if (!feedbackList.isEmpty()) {
//                int totalRating = feedbackList.stream()
//                        .mapToInt(Feedback::getRatingValue)
//                        .sum();
//                return totalRating / feedbackList.size();
//            } else {
//                return 0;
//            }
//        } catch (EntityNotFoundException e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
//        }
//    }

    @GetMapping("/ratedUser/{id}/ratings")
    public List<Integer> getRatingValuesForUser(@PathVariable int id) {
        return feedbackService.getRatingValuesForUser(id);
    }

    @GetMapping("/ratedUser/{id}/averageRating")
    public double getAverageRatingValueForUser(@PathVariable int id) {
        return feedbackService.getAverageRatingValueForUser(id);
    }

    @GetMapping("/topRatedDrivers")
    public List<User> getTopRatedUsers() {
        try {
            List<User> topRatedUsers = feedbackService.getTopRatedUsers();
            return topRatedUsers;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/topRatedPassengers")
    public List<User> getTopRatedPassengers() {
        try {
            List<User> topRatedPassengers = feedbackService.getTopRatedPassengers();
            return topRatedPassengers;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }





    @PostMapping("/driver-rating")
    public Feedback createDriverRating(@RequestHeader HttpHeaders headers, @Valid @RequestBody FeedbackDto feedbackDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Feedback feedback = feedbackMapper.fromFeedbackDto(feedbackDto);
            Trip trip = tripService.getTripById(feedback.getTripId());
            feedbackService.createFeedbackForDriver(feedback,trip, user);
            return feedback;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PostMapping("/passenger-rating")
    public Feedback createPassengerRating(@RequestHeader HttpHeaders headers, @Valid @RequestBody FeedbackDto feedbackDto) {
        try {
            User passenger = authenticationHelper.tryGetUser(headers); // Assuming the passenger is the one providing the rating
            Feedback feedback = feedbackMapper.fromFeedbackDto(feedbackDto);
            TripRequest tripRequest = tripRequestService.getTripRequestById(feedbackDto.getTripId());

            feedbackService.createFeedbackForPassenger(feedback, tripRequest, passenger);
            return feedback;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PostMapping("/{id}/add-comments")
    public FeedbackComment addComment (@RequestHeader HttpHeaders headers, @PathVariable int id,
                           @Valid @RequestBody FeedbackCommentDto feedbackCommentDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Feedback feedback = feedbackService.getFeedbackById(id);
            FeedbackComment feedbackComment = feedbackCommentMapper.fromFeedbackCommentDto(feedbackCommentDto);
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

//    @GetMapping("/{id}/comments")
//    public List<FeedbackComment> getAllComments(@PathVariable int id) {
//        return feedbackService.getAllFeedbackComments(id);
//    }


}