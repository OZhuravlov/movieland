package com.study.movieland.service.impl;

import com.study.movieland.dao.ReviewDao;
import com.study.movieland.entity.Movie;
import com.study.movieland.entity.Review;
import com.study.movieland.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultReviewService implements ReviewService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ReviewDao reviewDao;

    @Override
    public void enrich(Movie movie) {
        logger.debug("Enrich Movie with reviews");
        List<Review> reviews = reviewDao.getByMovieId(movie.getId());
        if (!Thread.currentThread().isInterrupted()) {
            logger.debug("Enrich Movie id {} with reviews: {}", movie.getId(), reviews);
            movie.setReviews(reviews);
        } else {
            logger.warn("Enrich Movie id {}. Thread was interrupted", movie.getId());
        }
    }

    @Override
    public void add(int movieId, Review review) {
        reviewDao.add(movieId, review);
        logger.debug("Add new review for Movie id {}: {}", movieId, review);
    }

    @Autowired
    public void setReviewDao(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

}
