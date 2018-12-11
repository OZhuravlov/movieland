package com.study.movieland.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.study.movieland.data.MovieRequestParam;
import com.study.movieland.data.SortDirection;
import com.study.movieland.entity.*;
import com.study.movieland.service.MovieService;
import com.study.movieland.view.Views;
import com.study.movieland.web.annotation.ProtectedBy;
import com.study.movieland.web.converter.CurrencyParamConverter;
import com.study.movieland.web.converter.SortDirectionConverter;
import com.study.movieland.web.data.MovieAddRequestData;
import com.study.movieland.web.data.MovieEditRequestData;
import com.study.movieland.web.exception.BadRequestParamException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/movie")
public class MovieController {

    private static final String RATING_FIELD_NAME = "rating";
    private static final String PRICE_FIELD_NAME = "price";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private MovieService movieService;

    @JsonView(Views.Summary.class)
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

    @JsonView(Views.Summary.class)
    @RequestMapping(value = "/random", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public List<Movie> getRandomMovies() {
        logger.info("Get random movies");
        List<Movie> movies = movieService.getRandom();
        logger.debug("Returning {} random movies", movies.size());
        return movies;
    }

    @JsonView(Views.Summary.class)
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

    @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ProtectedBy(allowedRoles = Role.ADMIN)
    public void addMovie(@RequestBody MovieAddRequestData movieAddRequestData) {
        logger.info("putMovie new movie");
        Movie movie = new Movie();
        movie.setNameNative(movieAddRequestData.getNameNative());
        movie.setNameRussian(movieAddRequestData.getNameRussian());
        movie.setYearOfRelease(movieAddRequestData.getYearOfRelease());
        movie.setDescription(movieAddRequestData.getDescription());
        movie.setPrice(movieAddRequestData.getPrice());
        movie.setPicturePath(movieAddRequestData.getPicturePath());
        movie.setCountries(getCountries(movieAddRequestData.getCountries()));
        movie.setGenres(getGenres(movieAddRequestData.getGenres()));
        logger.debug("putMovie new movie {}", movie);
        movieService.add(movie);
    }

    @JsonView(Views.Summary.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ProtectedBy(allowedRoles = Role.ADMIN)
    public void editMovie(@RequestBody MovieEditRequestData movieEditRequestData,
                          @PathVariable int id) {
        logger.info("edit movie id {}", id);
        Movie movie = new Movie();
        movie.setId(id);
        movie.setNameNative(movieEditRequestData.getNameNative());
        movie.setNameRussian(movieEditRequestData.getNameRussian());
        movie.setPicturePath(movieEditRequestData.getPicturePath());
        movie.setCountries(getCountries(movieEditRequestData.getCountries()));
        movie.setGenres(getGenres(movieEditRequestData.getGenres()));
        logger.debug("edit movie {}", movie);
        movieService.edit(movie);
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

    private List<Country> getCountries(List<Integer> countryIds){
        List<Country> countries = new ArrayList<>();
        for (Integer countryId : countryIds) {
            Country country = new Country();
            country.setId(countryId);
            countries.add(country);
        }
        return countries;
    }

    private List<Genre> getGenres(List<Integer> genreIds){
        List<Genre> genres = new ArrayList<>();
        for (Integer genreId : genreIds) {
            genres.add(Genre.newBuilder().setId(genreId).build());
        }
        return genres;
    }
}
