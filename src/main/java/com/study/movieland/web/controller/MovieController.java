package com.study.movieland.web.controller;

import com.study.movieland.entity.Movie;
import com.study.movieland.entity.OrderBy;
import com.study.movieland.entity.SortDirection;
import com.study.movieland.exception.BadRequestParamException;
import com.study.movieland.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/movie")
public class MovieController {

    private final static String NO_ORDER = "NONE";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private MovieService movieService;

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public List<Movie> getAllMovies(@RequestParam(value = "rating", required = false, defaultValue = "NONE") String ratingSorting,
                                    @RequestParam(value = "price", required = false, defaultValue = "NONE") String priceSorting) {
        List<Movie> movies;
        OrderBy orderBy = createOrderBy(ratingSorting, priceSorting);
        logger.info("Get all movies");
        movies = movieService.getAll(orderBy);
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
                                        @RequestParam(value = "rating", required = false, defaultValue = "NONE") String ratingSorting,
                                        @RequestParam(value = "price", required = false, defaultValue = "NONE") String priceSorting) {
        List<Movie> movies;
        OrderBy orderBy = createOrderBy(ratingSorting, priceSorting);
        logger.info("Get movies by genre");
        movies = movieService.getByGenre(id, orderBy);

        logger.debug("Returning {} movie(s) for genreId {}", movies.size(), id);
        return movies;
    }

    protected OrderBy createOrderBy(String ratingSorting, String priceSorting) {
        if (NO_ORDER.equals(ratingSorting) && NO_ORDER.equals(priceSorting)) {
            return null;
        }

        String errorMessage = "Invalid sorting parameters";

        if (!NO_ORDER.equals(ratingSorting)) {
            String ratingSortingUpper = ratingSorting.toUpperCase();
            if (ratingSortingUpper.equalsIgnoreCase(SortDirection.DESC.name())) {
                return new OrderBy("rating", SortDirection.DESC);
            } else {
                logger.warn("Eligible sort direction param {} for rating", ratingSorting);
                throw new BadRequestParamException(errorMessage);
            }
        } else {
            try {
                SortDirection sortDirection = SortDirection.valueOf(priceSorting.toUpperCase());
                return new OrderBy("price", sortDirection);
            } catch (IllegalArgumentException e) {
                logger.warn("Invalid sort direction param {} for price", priceSorting);
                throw new BadRequestParamException(errorMessage);
            }

        }
    }

    @Autowired
    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }

}
