package com.telerikacademy.carpooling.controllers.rest;

import com.telerikacademy.carpooling.controllers.AuthenticationHelper;
import com.telerikacademy.carpooling.exceptions.AuthorizationException;
import com.telerikacademy.carpooling.exceptions.EntityNotFoundException;
import com.telerikacademy.carpooling.mappers.RatingMapper;
import com.telerikacademy.carpooling.models.Rating;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.models.dtos.RatingDto;
import com.telerikacademy.carpooling.services.interfaces.RatingService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService ratingService;
    private final RatingMapper ratingMapper;
    private final AuthenticationHelper authenticationHelper;

    public RatingController(RatingService ratingService, RatingMapper ratingMapper, AuthenticationHelper authenticationHelper) {
        this.ratingService = ratingService;
        this.ratingMapper = ratingMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @PostMapping
    public Rating create(@RequestHeader HttpHeaders headers, @Valid @RequestBody  RatingDto ratingDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Rating rating = ratingMapper.fromRatingDto(ratingDto);
            ratingService.create(rating, user);
            return rating;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}