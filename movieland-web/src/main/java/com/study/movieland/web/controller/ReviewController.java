package com.study.movieland.web.controller;

import com.study.movieland.entity.Review;
import com.study.movieland.service.ReviewService;
import com.study.movieland.web.data.ReviewRequestData;
import com.study.movieland.web.holder.UserHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/review")
public class ReviewController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ReviewService reviewService;

    @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void addReview(@RequestBody ReviewRequestData reviewRequestData) {
        logger.info("Add new review");
        Review review = new Review();
        review.setUser(UserHolder.getCurrentUser());
        review.setText(reviewRequestData.getText());
        reviewService.add(reviewRequestData.getMovieId(), review);
    }

    @Autowired
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

}
