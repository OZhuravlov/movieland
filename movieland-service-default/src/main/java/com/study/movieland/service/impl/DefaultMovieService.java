package com.study.movieland.service.impl;

import com.study.movieland.dao.MovieDao;
import com.study.movieland.data.MovieRequestParam;
import com.study.movieland.entity.Currency;
import com.study.movieland.entity.Genre;
import com.study.movieland.entity.Movie;
import com.study.movieland.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class DefaultMovieService implements MovieService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ExecutorService executor = Executors.newCachedThreadPool();

    private MovieDao movieDao;
    private GenreService genreService;
    private CountryService countryService;
    private ReviewService reviewService;
    private CurrencyService currencyService;
    private int executorTimeOutInSeconds;

    @Override
    public List<Movie> getAll(MovieRequestParam movieRequestParam) {
        logger.info("get All Movies");
        long startTime = System.currentTimeMillis();
        List<Movie> movies = movieDao.getAll(movieRequestParam);
        logger.debug("Query took:{}", System.currentTimeMillis() - startTime);
        return movies;
    }

    @Override
    public List<Movie> getRandom() {
        logger.info("get Random Movies");
        long startTime = System.currentTimeMillis();
        List<Movie> movies = movieDao.getRandom();
        logger.debug("Query took:{}", System.currentTimeMillis() - startTime);
        return movies;
    }

    @Override
    public List<Movie> getByGenre(int genreId, MovieRequestParam movieRequestParam) {
        logger.info("get Movies by Genre");
        logger.info("check Genre for id {}", genreId);
        Genre genre = genreService.getById(genreId);
        long startTime = System.currentTimeMillis();
        logger.debug("Query took:{}", System.currentTimeMillis() - startTime);
        return movieDao.getByGenreId(genre.getId(), movieRequestParam);
    }

    @Override
    public Movie getById(int id, MovieRequestParam movieRequestParam) {
        logger.info("get Movies by id {}", id);
        Movie movie = movieDao.getById(id);
        List<Callable<Boolean>> tasks = Arrays.asList(
                () -> {
                    countryService.enrichMovie(movie);
                    return true;
                },
                () -> {
                    genreService.enrichMovie(movie);
                    return true;
                },
                () -> {
                    reviewService.enrichMovie(movie);
                    return true;
                }
        );
        try {
            executor.invokeAll(tasks, executorTimeOutInSeconds, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error("Error", e);
        }
        Currency currency = movieRequestParam.getCurrency();
        double convertedPrice = currencyService.getConvertedPrice(movie.getPrice(), currency);
        movie.setPrice(convertedPrice);
        movie.setCurrency(currency);
        return movie;
    }

    @Override
    public void add(Movie movie) {
        logger.info("add new movie {}", movie.getNameNative());
        logger.debug("movie values {}", movie);
        movieDao.add(movie);
        countryService.addReference(movie);
        genreService.addReference(movie);
    }

    @Override
    public void edit(Movie movie) {
        logger.info("edit movie id {}", movie.getId());
        logger.debug("movie values: {}", movie);
        movieDao.edit(movie);
        countryService.editReference(movie);
        genreService.editReference(movie);
    }

    @Autowired
    public void setMovieDao(MovieDao movieDao) {
        this.movieDao = movieDao;
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
    public void setCurrencyService(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Value("${service.movie.executor.timeOutInSeconds}")
    public void setExecutorTimeOutInSeconds(int executorTimeOutInSeconds) {
        this.executorTimeOutInSeconds = executorTimeOutInSeconds;
    }
}