package com.study.movieland.service.impl;

import com.study.movieland.entity.Movie;
import com.study.movieland.service.CountryService;
import com.study.movieland.service.EnrichService;
import com.study.movieland.service.GenreService;
import com.study.movieland.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultEnrichService implements EnrichService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private GenreService genreService;
    private CountryService countryService;
    private ReviewService reviewService;

    @Override
    public void enrich(Movie movie) {
        logger.info("Enrich movie");
        genreService.enrich(movie);
        countryService.enrich(movie);
        reviewService.enrich(movie);
    }

    @Autowired
    public void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }

    @Autowired
    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    @Autowired
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

}
