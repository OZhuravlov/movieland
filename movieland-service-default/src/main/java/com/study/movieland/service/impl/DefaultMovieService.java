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

import java.lang.ref.SoftReference;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Service
public class DefaultMovieService implements MovieService {

    private static final Map<Integer, SoftReference<Movie>> MOVIE_CACHE = new ConcurrentHashMap<>();

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
        Movie movie;
        SoftReference<Movie> movieSoftReference = MOVIE_CACHE.get(id);
        if (movieSoftReference != null && movieSoftReference.get() != null) {
            movie = movieSoftReference.get();
            logger.info("Got movie id {} from cache", id);
        } else {
            logger.info("getting movie id {} from main source", id);
            movie = movieDao.getById(id);
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
                List<Future<Boolean>> result = executor.invokeAll(tasks, executorTimeOutInSeconds, TimeUnit.SECONDS);
                if (result.stream().noneMatch(Future::isCancelled)) {
                    MOVIE_CACHE.put(id, new SoftReference<>(movie));
                    logger.info("Put movie id {} in cache", movie.getId());
                }
            } catch (InterruptedException e) {
                logger.error("Error", e);
            }
        }
        Movie movieCopy = new Movie(movie);
        Currency currency = movieRequestParam.getCurrency();
        double convertedPrice = currencyService.getConvertedPrice(movieCopy.getPrice(), currency);
        movieCopy.setPrice(convertedPrice);
        movieCopy.setCurrency(currency);
        return movieCopy;
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
        int id = movie.getId();
        if (MOVIE_CACHE.containsKey(id)) {
            MOVIE_CACHE.remove(id);
            MovieRequestParam requestParam = new MovieRequestParam();
            requestParam.setCurrency(Currency.getDefault());
            MOVIE_CACHE.put(id, new SoftReference<>(getById(id, requestParam)));
            logger.info("replace movie id {} in cache {}", id);
        }
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