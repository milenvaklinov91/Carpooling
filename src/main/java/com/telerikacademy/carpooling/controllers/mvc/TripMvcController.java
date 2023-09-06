package com.telerikacademy.carpooling.controllers.mvc;

import com.telerikacademy.carpooling.controllers.AuthenticationHelper;
import com.telerikacademy.carpooling.exceptions.AuthorizationException;
import com.telerikacademy.carpooling.exceptions.EntityDuplicateException;
import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.exceptions.UnauthorizedOperationException;
import com.telerikacademy.carpooling.mappers.TripMapper;
import com.telerikacademy.carpooling.models.Trip;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.dtos.TripDto;
import com.telerikacademy.carpooling.models.dtos.TripFilterDto;
import com.telerikacademy.carpooling.models.filterOptions.TripFilterOptions;
import com.telerikacademy.carpooling.services.interfaces.TripService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/trips")
public class TripMvcController {

    private final TripService tripService;
    private final TripMapper tripMapper;
    private final AuthenticationHelper authenticationHelper;

    public TripMvcController(TripService tripService, TripMapper tripMapper, AuthenticationHelper authenticationHelper) {
        this.tripService = tripService;
        this.tripMapper = tripMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    @GetMapping()
    public String showAllTrips(@ModelAttribute("filter") TripFilterDto filter, Model model, HttpSession session) {
        TripFilterOptions tripFilterOptions = new TripFilterOptions(
                filter.getStartLocation(),
                filter.getEndLocation(),
                filter.getDepartureTime(),
                filter.getCostPerPerson(),
                filter.getUsername(),
                filter.getSortBy(),
                filter.getSortOrder());

        List<Trip> trips = tripService.getAll(tripFilterOptions);
        model.addAttribute("trips", trips);
        model.addAttribute("filter", filter);
        return "AllTripsView";
    }


    @GetMapping("/{id}")
    public String showSingleTrip(@PathVariable int id, Model model, HttpSession session) {
        try {
          User user = authenticationHelper.tryGetCurrentUser(session);
          Trip trip = tripService.getTripById(id);
          model.addAttribute("trip",trip);
            return "singleTripView";
        } catch (AuthorizationException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        }

    }

    @GetMapping("/new-trip")
    public String showNewTripPage(Model model, HttpSession session) {
       /* TripDto trip = (TripDto) session.getAttribute("currentTrip");*/
        try {
            authenticationHelper.tryGetCurrentUser(session);
//            if (trip == null) {
//                trip = new TripDto();
//            } else {
//                session.removeAttribute("currentTrip");
//            }

        } catch (AuthorizationException e) {
            return "redirect:auth/login";
        }
        model.addAttribute("trip", new TripDto());
        return "createTrip";
    }

    @PostMapping("/new-trip")
    public String createTrip(@Valid @ModelAttribute("trip") TripDto trip, BindingResult errors,
                             Model model, HttpSession session) {
        User user;
        try {
           user = authenticationHelper.tryGetCurrentUser(session);
        } catch (AuthorizationException e) {
            return "redirect:auth/login";
        }
        if (errors.hasErrors()) {
            return "createTrip";
        }
        try {
            Trip newTrip = tripMapper.fromTripDto(trip);
            tripService.create(newTrip, user);
            return "redirect:/trips"; //todo
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found"; //todo
        } catch (EntityDuplicateException e) {
            errors.rejectValue("name", "duplicate_trip", e.getMessage());
            return "createTrip"; //todo
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }
    }

    @GetMapping("/{id}/update")
    public String showEditTripPage(@PathVariable int id, Model model, HttpSession session) {
        try {
            authenticationHelper.tryGetCurrentUser(session);
        } catch (AuthorizationException e) {
            return "redirect:auth/login";
        }
        try {
            Trip trip = tripService.getTripById(id);
            /*TripDto tripDto = tripMapper.fromTripDtoUpdate(trip,tripDto);*/ //todo
            model.addAttribute("tripId", id);
            /*model.addAttribute("trip", tripDto);*/
            return "trip-update";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        }
    }

    @PostMapping("/{id}/update")
    public String updateTrip(@PathVariable int id, @Valid @ModelAttribute("trip") TripDto trip, BindingResult errors,
                             Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetCurrentUser(session);
        } catch (AuthorizationException e) {
            return "redirect:auth/login";
        }
        if (errors.hasErrors()) {
            return "trip-update"; //todo
        }
        try {
            Trip newTrip = tripMapper.fromDto(id, trip);
            tripService.modify(newTrip, user);
            return "redirect:/trips";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        } catch (EntityDuplicateException e) {
            errors.rejectValue("name", "duplicate_trip", e.getMessage());
            return "trip-update";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }

    }

    @GetMapping("/{id}/delete")
    public String deleteTrip(@PathVariable int id, Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetCurrentUser(session);
        } catch (AuthorizationException e) {
            return "redirect:auth/login";
        }
        try {
            tripService.delete(id, user);
            return "redirect:/trips";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }
    }

    @GetMapping("/{id}/in-progress")
    public String inProgressTripStatus(HttpSession session, @PathVariable int id, Model model) {
        try {
            User user = authenticationHelper.tryGetCurrentUser(session);
            Trip trip = tripService.getTripById(id); //todo
            model.addAttribute("trip", trip);
            tripService.inProgressTripStatus(trip, user);
            return "singleTripView";
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            return "redirect:/login";
        }
    }

    @GetMapping("/{id}/finished")
    public String finishedTripStatus(HttpSession session, @PathVariable int id, Model model) {
        try {
            User user = authenticationHelper.tryGetCurrentUser(session);
            Trip trip = tripService.getTripById(id); //todo
            model.addAttribute("trip", trip);
            tripService.finishedTripStatus(trip, user);
            return "singleTripView";
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            return "redirect:/login";
        }
    }

    /*@GetMapping("/{travelId}/createFeedback/{recipientId}")
    public String showFeedback(HttpSession session,
                               @PathVariable int travelId, @PathVariable int recipientId,
                               Model model) {
        User loggedUser;
        try {
            loggedUser = authenticationHelper.tryGetCurrentUser(session);
        } catch (AuthorizationException e) {
            return "redirect:/";
        }
        try {
            Travel travel = travelService.get(travelId);
            User recipientFb = userService.get(recipientId);
            model.addAttribute("travel", travel);
            model.addAttribute("recipient", recipientFb);
            model.addAttribute("feedback", new Feedback());
            return "TravelView";

        } catch (AuthorizationException | TravelException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        }
    }

    @PostMapping("/{travelId}/createFeedback/{recipientId}")
    public String createFeedback(HttpSession session,
                                 @PathVariable int travelId, @PathVariable int recipientId,
                                 @Valid @ModelAttribute("feedback") FeedbackDto feedback,
                                 BindingResult bindingResult,
                                 Model model) {
        User loggedUser;
        try {
            loggedUser = authenticationHelper.tryGetCurrentUser(session);
        } catch (AuthorizationException e) {
            return "redirect:/";
        }
        try {
            Travel travel = travelService.get(travelId);
            model.addAttribute("travel", travel);
            if (bindingResult.hasErrors()) {
                return "TravelView";
            }
            feedbackService.create(loggedUser, userService.get(recipientId), feedbackMapper.mapFromDto(feedback), travelId);
            return "redirect:/travels/{travelId}";
        } catch (AuthorizationException | TravelException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        }
    }

    @GetMapping("{id}/apply")
    public String applyForTravel(HttpSession httpSession, Model model, @PathVariable int id) {
        try {
            User loggedUser = authenticationHelper.tryGetCurrentUser(httpSession);
            Travel travel = travelService.get(id);
            model.addAttribute("travel", travelMapper.mapToDto(travelService.get(id)));
            travelService.applyForTravel(travel, loggedUser);
            return "redirect:/travels/{id}";
        } catch (AuthorizationException e) {
            return "redirect:/login";
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (TravelException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }
    }

    @GetMapping("{id}/cancel-apply")
    public String cancelTravel(HttpSession httpSession, Model model, @PathVariable int id) {
        try {
            User loggedUser = authenticationHelper.tryGetCurrentUser(httpSession);
            Travel travel = travelService.get(id);
            model.addAttribute("travel", travelMapper.mapToDto(travelService.get(id)));
            travelService.cancelForTravel(travel, loggedUser);
            return "redirect:/travels/{id}";
        } catch (AuthorizationException e) {
            return "redirect:/login";
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (TravelException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }
    }

    @GetMapping("{travelId}/accept-apply/{candidateId}")
    public String acceptCandidate(HttpSession httpSession, Model model, @PathVariable int travelId, @PathVariable int candidateId) {
        try {
            User loggedUser = authenticationHelper.tryGetCurrentUser(httpSession);
            User candidate = userService.get(candidateId);
            Travel travel = travelService.get(travelId);
            model.addAttribute("travel", travelMapper.mapToDto(travelService.get(travelId)));
            travelService.acceptCandidate(travel, loggedUser, candidate);
            return "redirect:/travels/{travelId}";
        } catch (AuthorizationException e) {
            return "redirect:/login";
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (TravelException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }
    }

    @GetMapping("{travelId}/decline-apply/{passengerId}")
    public String declinePassenger(HttpSession httpSession, Model model, @PathVariable int travelId, @PathVariable int passengerId) {
        try {
            User loggedUser = authenticationHelper.tryGetCurrentUser(httpSession);
            User passenger = userService.get(passengerId);
            Travel travel = travelService.get(travelId);
            model.addAttribute("travel", travelMapper.mapToDto(travelService.get(travelId)));
            travelService.declinePassenger(travel, loggedUser, passenger);
            return "redirect:/travels/{travelId}";
        } catch (AuthorizationException e) {
            return "redirect:/login";
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (TravelException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }
    }
    @GetMapping("/myTravels")
    public String getMyTravels(@ModelAttribute("filter") FilterTravelDto filter, Model model, HttpSession httpSession) {
        FilterOptionsTravels filterOptions = new FilterOptionsTravels(
                filter.getStartCity(),
                filter.getEndCity(),
                filter.getDepartureTime(),
                filter.getDescription(),
                filter.getComplete(),
                filter.getSortBy(),
                filter.getSortOrder());
        try {
            User loggedUser = authenticationHelper.tryGetCurrentUser(httpSession);
            List<TravelMyDto> travels = travelMapper.mapToMyDto(travelService.getMyTravels(filterOptions, loggedUser.getId()));
            model.addAttribute("filter", filter);
            model.addAttribute("travels", travels);
            return "MyTravelsView";
        } catch (AuthorizationException e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/login";
        }
    }*/


}
