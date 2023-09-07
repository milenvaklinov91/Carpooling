package com.telerikacademy.carpooling.controllers.mvc;

import com.telerikacademy.carpooling.controllers.AuthenticationHelper;
import com.telerikacademy.carpooling.exceptions.AuthorizationException;
import com.telerikacademy.carpooling.exceptions.EntityDuplicateException;
import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.exceptions.UnauthorizedOperationException;
import com.telerikacademy.carpooling.mappers.TripRequestMapper;
import com.telerikacademy.carpooling.models.TripRequest;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.dtos.TripRequestDto;
import com.telerikacademy.carpooling.services.interfaces.TripRequestService;
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
@RequestMapping("/trip-requests")
public class TripRequestMvcController {
    private final TripRequestService tripRequestService;
    private final AuthenticationHelper authenticationHelper;

    private final TripRequestMapper tripRequestMapper;

    public TripRequestMvcController(TripRequestService tripRequestService, AuthenticationHelper authenticationHelper, TripRequestMapper tripRequestMapper) {
        this.tripRequestService = tripRequestService;
        this.authenticationHelper = authenticationHelper;
        this.tripRequestMapper = tripRequestMapper;
    }

    @GetMapping("/{id}")
    public String showSingleTripRequest(HttpSession session, Model model, @PathVariable int id) {
        try {
            authenticationHelper.tryGetCurrentUser(session);
        } catch (AuthorizationException e) {
            return "redirect:/";
        }
        try {
            TripRequest trip = tripRequestService.getTripRequestById(id);
            model.addAttribute("tripRequests", trip);
            return "singleTripView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        }
    }

    @GetMapping
    public String showAllTripRequest(Model model) {
        List<TripRequest> tripRequests = tripRequestService.getAll();
        model.addAttribute("tripRequests", tripRequests);
        return "allTripRequests";
    }

    @GetMapping("/new")
    public String showNewTripRequestPage(Model model, HttpSession session) {
        TripRequestDto tripRequest = (TripRequestDto) session.getAttribute("currentTripRequest");
        try {
            authenticationHelper.tryGetCurrentUser(session);
            if (tripRequest == null) {
                tripRequest = new TripRequestDto();
            } else {
                session.removeAttribute("currentTripRequest");
            }

        } catch (AuthorizationException e) {
            return "redirect:auth/login";
        }
        model.addAttribute("tripRequest", tripRequest);
        return "trip-request-new";
    }

    @PostMapping("/new")
    public String createTripRequest(@Valid @ModelAttribute("tripRequest") TripRequestDto tripRequest, BindingResult errors,
                                    Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetCurrentUser(session);
        } catch (AuthorizationException e) {
            return "redirect:auth/login";
        }
        if (errors.hasErrors()) {
            return "trip-request-new";
        }
        try {
            TripRequest newTripRequest = tripRequestMapper.fromTripRequestDto(tripRequest);
            tripRequestService.create(newTripRequest, user);
            return "redirect:/trip-requests";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        } catch (EntityDuplicateException e) {
            errors.rejectValue("name", "duplicate_tripRequest", e.getMessage());
            return "trip-request-new";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteTripRequest(@PathVariable int id, Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetCurrentUser(session);
        } catch (AuthorizationException e) {
            return "redirect:auth/login";
        }
        try {
            tripRequestService.delete(id, user);
            return "redirect:/trip-requests";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }
    }

    @GetMapping("{id}/approve")
    public String approveTripRequest(HttpSession session, Model model, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetCurrentUser(session);
            TripRequest tripRequest = tripRequestService.getTripRequestById(id);
            model.addAttribute("tripRequest", tripRequestService.getTripRequestById(id));
            tripRequestService.approveTripRequest(tripRequest, user);
            return "trip-requestsView";
        } catch (AuthorizationException e) {
            return "redirect:/login";
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("{id}/reject")
    public String rejectTripRequest(HttpSession httpSession, Model model, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetCurrentUser(httpSession);
            TripRequest tripRequest = tripRequestService.getTripRequestById(id);
            model.addAttribute("tripRequest", tripRequestService.getTripRequestById(id));
            tripRequestService.rejectTripRequest(tripRequest, user);
            return "trip-requestView";
        } catch (AuthorizationException e) {
            return "redirect:/login";
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
