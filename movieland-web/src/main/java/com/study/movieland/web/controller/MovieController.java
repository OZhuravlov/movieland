package com.study.movieland.web.controller;

import com.study.movieland.data.MovieRequestParam;
import com.study.movieland.data.SortDirection;
import com.study.movieland.entity.Currency;
import com.study.movieland.entity.Movie;
import com.study.movieland.service.MovieService;
import com.study.movieland.web.converter.CurrencyParamConverter;
import com.study.movieland.web.converter.SortDirectionConverter;
import com.study.movieland.web.exception.BadRequestParamException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/movie")
public class MovieController {

    private static final String RATING_FIELD_NAME = "rating";
    private static final String PRICE_FIELD_NAME = "price";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private MovieService movieService;

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public List<Movie> getAllMovies(@RequestParam(value = "rating", required = false, defaultValue = "NONE") SortDirection ratingSorting,
                                    @RequestParam(value = "price", required = false, defaultValue = "NONE") SortDirection priceSorting) {
        List<Movie> movies;
        MovieRequestParam movieRequestParam = createMovieRequestParam(ratingSorting, priceSorting);
        logger.info("Get all movies");
        movies = movieService.getAll(movieRequestParam);
        logger.debug("Returning {} movies", movies.size());
        return movies;
    }

    @RequestMapping(value = "/random", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public List<Movie> getRandomMovies() {
        logger.info("Get random movies");
        List<Movie> movies = movieService.getRandom();
        logger.debug("Returning {} random movies", movies.size());
        return movies;
    }

    @RequestMapping(value = "/genre/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public List<Movie> getMoviesByGenre(@PathVariable int id,
                                        @RequestParam(value = "rating", required = false, defaultValue = "NONE") SortDirection ratingSorting,
                                        @RequestParam(value = "price", required = false, defaultValue = "NONE") SortDirection priceSorting) {
        MovieRequestParam movieRequestParam = createMovieRequestParam(ratingSorting, priceSorting);
        logger.info("Get movies by genre");
        List<Movie> movies = movieService.getByGenre(id, movieRequestParam);
        logger.debug("Returning {} movie(s) for genreId {}", movies.size(), id);
        return movies;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Movie getMoviesById(@PathVariable int id,
                               @RequestParam(value = "currency", required = false, defaultValue = "UAH") Currency currency) {
        MovieRequestParam movieRequestParam = new MovieRequestParam();
        movieRequestParam.setCurrency(currency);
        logger.info("Get movies by id {}", id);
        Movie movie = movieService.getById(id, movieRequestParam);
        logger.trace("Returning movie {}", movie);
        return movie;
    }

    MovieRequestParam createMovieRequestParam(SortDirection ratingSort, SortDirection priceSort) {
        if (ratingSort == null && priceSort == null) {
            return null;
        }
        MovieRequestParam movieRequestParam = new MovieRequestParam();
        if (ratingSort != null) {
            if (SortDirection.DESC.equals(ratingSort)) {
                movieRequestParam.setSortFieldName(RATING_FIELD_NAME);
                movieRequestParam.setSortDirection(ratingSort);
                return movieRequestParam;
            } else {
                logger.warn("Eligible sort direction param {} for rating", ratingSort);
                throw new BadRequestParamException("Invalid sorting directing");
            }
        }
        movieRequestParam.setSortFieldName(PRICE_FIELD_NAME);
        movieRequestParam.setSortDirection(priceSort);
        return movieRequestParam;
    }

    @InitBinder
    public void initBinder(final WebDataBinder webdataBinder) {
        webdataBinder.registerCustomEditor(SortDirection.class, new SortDirectionConverter());
        webdataBinder.registerCustomEditor(Currency.class, new CurrencyParamConverter());
    }

    @Autowired
    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }

}
