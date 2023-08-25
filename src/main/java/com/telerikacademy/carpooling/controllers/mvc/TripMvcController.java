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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/trips")
public class TripMvcController {

    private final TripService tripService;
    private final TripMapper tripMapper;
    AuthenticationHelper authenticationHelper;
    public TripMvcController(TripService tripService, TripMapper tripMapper) {
        this.tripService = tripService;
        this.tripMapper = tripMapper;
    }

    @GetMapping()
    public String showAllTrips(@ModelAttribute("tripFilterOptions") TripFilterDto tripFilterDto, Model model, HttpSession session) {
        TripFilterOptions tripFilterOptions = new TripFilterOptions(
                tripFilterDto.getStartLocation(),
                tripFilterDto.getEndLocation(),
                tripFilterDto.getDepartureTime(),
                tripFilterDto.getCostPerPerson(),
                tripFilterDto.getUsername(),
                tripFilterDto.getSortBy(),
                tripFilterDto.getSortOrder());


        List<Trip> trips = tripService.getAll(tripFilterOptions);
        model.addAttribute("trips", trips);
        model.addAttribute("tripFilterOptions", tripFilterOptions);
        return "AllTripsView";
    }

    @GetMapping("/{id}")
    public String showSingleTrip(@PathVariable int id, Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetCurrentUser(session);
        } catch (AuthorizationException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        }
        return "post";
    }

    @GetMapping("/new")
    public String showNewTripPage(Model model, HttpSession session) {
        TripDto trip = (TripDto) session.getAttribute("currentPost");
        try {
            authenticationHelper.tryGetCurrentUser(session);
            if (trip == null) {
                trip = new TripDto();
            } else {
                session.removeAttribute("currentPost");
            }

        } catch (AuthorizationException e) {
            return "redirect:auth/login";
        }
        model.addAttribute("trip", trip);
        return "trip-new";
    }

    @PostMapping("/new")
    public String createTrip(@Valid @ModelAttribute("trip") TripDto trip, BindingResult errors,
                             Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetCurrentUser(session);
        } catch (AuthorizationException e) {
            return "redirect:auth/login";
        }
        if (errors.hasErrors()) {
            return "trip-new";
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
            return "trip-new"; //todo
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

}
