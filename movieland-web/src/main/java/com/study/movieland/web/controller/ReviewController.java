package com.study.movieland.web.controller;

import com.study.movieland.data.Session;
import com.study.movieland.entity.Role;
import com.study.movieland.service.ReviewService;
import com.study.movieland.service.SecurityService;
import com.study.movieland.web.data.ReviewRequestData;
import com.study.movieland.web.exception.OperationNotAllowedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/review")
public class ReviewController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ReviewService reviewService;
    private SecurityService securityService;

    @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void addReview(@RequestHeader("uuid") String token,
                          @RequestBody ReviewRequestData reviewRequestData) {
        logger.info("Add new review");
        Optional<Session> sessionOptional = securityService.getSession(token);
        sessionOptional.filter(s -> s.getUser().getRole().equals(Role.USER) || s.getUser().getRole().equals(Role.ADMIN))
                .orElseThrow(() -> new OperationNotAllowedException("Operation is not allowed"));
        reviewService.add(reviewRequestData.getMovieId(),
                sessionOptional.get().getUser().getId(),
                reviewRequestData.getText());
    }

    @Autowired
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}
