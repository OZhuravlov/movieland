package com.study.movieland.service.impl;

import com.study.movieland.dao.CountryDao;
import com.study.movieland.dao.GenreDao;
import com.study.movieland.dao.MovieDao;
import com.study.movieland.dao.ReviewDao;
import com.study.movieland.entity.*;
import com.study.movieland.service.CountryService;
import com.study.movieland.service.GenreService;
import com.study.movieland.service.ReviewService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

public class DefaultMovieServiceTest {

    Movie movie;
    Movie expectedMovie;
    @Mock
    MovieDao movieDao;
    @Mock
    CountryDao countryDao;
    @Mock
    CountryService countryService;
    @Mock
    GenreDao genreDao;
    @Mock
    GenreService genreService;
    @Mock
    ReviewDao reviewDao;
    @Mock
    ReviewService reviewService;

    @InjectMocks
    DefaultMovieService movieService;

    @Before
    public void prepareMocks() {
        MockitoAnnotations.initMocks(this);
        movie = getMockMovie();
        expectedMovie = getEnrichedMockMovie(movie);

        when(movieDao.getById(anyInt())).thenReturn(movie);

        // Mock Country + Dao + Service
        List<Integer> countryIds = Arrays.asList(1, 2, 3);
        when(movieDao.getCountryIds(anyInt())).thenReturn(countryIds);
        List<Country> countries = getMockCountries(countryIds);
        for (Country country : countries) {
            when(countryDao.getById(country.getId())).thenReturn(country);
        }
        doAnswer(invocation -> {
            movie.setCountries(countries);
            return null;
        }).when(countryService).enrichMovie(movie, countryIds);

        // Mock Genre + Dao + Service
        List<Integer> genreIds = Arrays.asList(4, 5, 6);
        when(movieDao.getGenreIds(anyInt())).thenReturn(genreIds);
        List<Genre> genres = getMockGenres(genreIds);
        for (Genre genre : genres) {
            when(genreDao.getById(genre.getId())).thenReturn(genre);
        }
        doAnswer(invocation -> {
            movie.setGenres(genres);
            return null;
        }).when(genreService).enrichMovie(movie, genreIds);

        // Mock Review + Dao + Service
        List<Review> reviews = getMockReviews();
        when(reviewDao.getByMovieId(anyInt())).thenReturn(reviews);
        doAnswer(invocation -> {
            movie.setReviews(reviews);
            return null;
        }).when(reviewService).enrichMovie(movie);

    }

    @Test
    public void getById() {
        movieService = new DefaultMovieService();
        movieService.setCountryService(countryService);
        movieService.setMovieDao(movieDao);
        movieService.setGenreService(genreService);
        movieService.setReviewService(reviewService);
        assertThat(movieService.getById(1), is(expectedMovie));

    }

    private Movie getMockMovie() {
        Movie movie = new Movie();
        movie.setId(1);
        movie.setNameNative("Name Native");
        movie.setNameRussian("Русское название");
        movie.setYearOfRelease(2001);
        movie.setRating(1.23);
        movie.setPrice(45.67);
        movie.setPicturePath("path/to/picture");
        return movie;
    }

    private Movie getEnrichedMockMovie(Movie movie) {
        Movie newMovie = movie;
        newMovie.setId(1);
        newMovie.setNameNative("Name Native");
        newMovie.setNameRussian("Русское название");
        newMovie.setYearOfRelease(2001);
        newMovie.setRating(1.23);
        newMovie.setPrice(45.67);
        newMovie.setPicturePath("path/to/picture");
        return movie;
    }

    private List<Country> getMockCountries(List<Integer> countryIds) {
        List<Country> countries = new ArrayList<>();
        for (Integer countryId : countryIds) {
            Country country = new Country();
            country.setId(countryId);
            country.setName("Name " + countryId);
            countries.add(country);
        }
        return countries;
    }

    private List<Genre> getMockGenres(List<Integer> genreIds) {
        List<Genre> genres = new ArrayList<>();
        for (Integer genreId : genreIds) {
            genres.add(Genre.newBuilder()
                    .setId(genreId)
                    .setName("Name " + genreId)
                    .build());
        }
        return genres;
    }

    private List<Review> getMockReviews() {
        List<Review> reviews = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            Review review = new Review();
            review.setId(i);
            review.setText("Text " + i);
            User user = new User();
            user.setId(1);
            user.setNickname("Nickname 1");
            review.setUser(user);
            reviews.add(review);
        }
        return reviews;
    }

}