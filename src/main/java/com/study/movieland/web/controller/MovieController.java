package com.study.movieland.web.controller;

import com.study.movieland.entity.Movie;
import com.study.movieland.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.InvalidParameterException;
import java.util.List;

@RestController
@RequestMapping(value = "/movie")
public class MovieController {

    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    private MovieService movieService;

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public List<Movie> getAllMovies() {
        logger.info("Get all movies GET request");
        List<Movie> movies = movieService.getAll();
        logger.debug("Returning {} movies", movies.size());
        return movies;
    }

    @RequestMapping(value = "/random", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public List<Movie> getRandomMovies() {
        logger.debug("Get random movies GET request");
        List<Movie> movies = movieService.getRandom();
        logger.debug("Returning {} random movies", movies.size());
        return movies;
    }

    @RequestMapping(value = "/genre/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<List<Movie>> getMoviesByGenre(@PathVariable int id, HttpServletResponse response) {
        try {
            logger.debug("Try to get movies by genreId {} GET request", id);
            List<Movie> movies = movieService.getByGenre(id);
            logger.debug("Returning {} movie(s) for genreId {}", movies.size(), id);
            return ResponseEntity.status(HttpStatus.OK).body(movies);
        } catch (InvalidParameterException e) {
            logger.error("Invalid Path Variable value {}. No such genre", id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Autowired
    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }
}
