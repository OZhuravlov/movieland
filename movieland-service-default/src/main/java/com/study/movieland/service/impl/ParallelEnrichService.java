package com.study.movieland.service.impl;

import com.study.movieland.entity.Movie;
import com.study.movieland.service.CountryService;
import com.study.movieland.service.EnrichService;
import com.study.movieland.service.GenreService;
import com.study.movieland.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Service
@Primary
public class ParallelEnrichService implements EnrichService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ExecutorService executorService;
    private GenreService genreService;
    private CountryService countryService;
    private ReviewService reviewService;
    private int executorTimeOutInSeconds;

    @Override
    public boolean enrich(Movie movie) {
        boolean isEnrichSuccess = false;
        logger.info("Enrich movie in parallel");
        List<Callable<Boolean>> tasks = Arrays.asList(
                () -> {
                    countryService.enrich(movie);
                    return true;
                },
                () -> {
                    genreService.enrich(movie);
                    return true;
                },
                () -> {
                    reviewService.enrich(movie);
                    return true;
                }
        );
        try {
            List<Future<Boolean>> result = executorService.invokeAll(tasks, executorTimeOutInSeconds, TimeUnit.SECONDS);
            if (result.stream().noneMatch(Future::isCancelled)) {
                isEnrichSuccess = true;
                logger.info("Enrichment succeed", movie.getId());
            }
        } catch (InterruptedException e) {
            logger.error("Error", e);
        }
        return isEnrichSuccess;
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

    @Autowired
    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Value("${service.enrich.executorService.timeOutInSeconds}")
    public void setExecutorTimeOutInSeconds(int executorTimeOutInSeconds) {
        this.executorTimeOutInSeconds = executorTimeOutInSeconds;
    }
}

