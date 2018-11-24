package com.study.movieland.web.controller;

import com.study.movieland.entity.Movie;
import com.study.movieland.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/movie")
public class MovieController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private MovieService movieService;

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public List<Movie> getAllMovies() {
        logger.info("Get all movies");
        List<Movie> movies = movieService.getAll();
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
    public List<Movie> getMoviesByGenre(@PathVariable int id) {
        logger.debug("Try to get movies by genreId {} GET request", id);
        List<Movie> movies = movieService.getByGenre(id);
        logger.debug("Returning {} movie(s) for genreId {}", movies.size(), id);
        return movies;
    }

    @Autowired
    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }
}
