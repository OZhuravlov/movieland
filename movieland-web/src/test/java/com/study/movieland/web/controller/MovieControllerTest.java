package com.study.movieland.web.controller;

import com.study.movieland.entity.*;
import com.study.movieland.service.MovieService;
import com.study.movieland.web.exception.BadRequestParamException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class MovieControllerTest {

    @Mock
    MovieService movieService;

    @InjectMocks
    MovieController movieController = new MovieController();

    private MockMvc mockMvc;

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(movieController)
                .build();
    }

    @Test
    public void getAllMoviesTest() throws Exception {
        List<Movie> movies = Arrays.asList(
                new Movie(1, "Movie 1", "Фильм 1", 2001, 1.21, 23.41, "path/to/picture/1"),
                new Movie(2, "Movie 2", "Фильм 2", 2002, 1.22, 23.42, "path/to/picture/2"));
        when(movieService.getAll(null)).thenReturn(movies);
        mockMvc.perform(get("/movie"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nameNative", is("Movie 1")))
                .andExpect(jsonPath("$[0].nameRussian", is("Фильм 1")))
                .andExpect(jsonPath("$[0].yearOfRelease", is(2001)))
                .andExpect(jsonPath("$[0].rating", is(1.21)))
                .andExpect(jsonPath("$[0].price", is(23.41)))
                .andExpect(jsonPath("$[0].picturePath", is("path/to/picture/1")));
        verify(movieService, times(1)).getAll(null);
        verifyNoMoreInteractions(movieService);
    }

    @Test
    public void getRandomMoviesTest() throws Exception {
        List<Movie> movies = Arrays.asList(
                new Movie(1, "Random Movie 1", "Случайный Фильм 1", 2001, 1.21, 23.41, "path/to/picture/1"),
                new Movie(2, "Random Movie 2", "Случайный Фильм 2", 2002, 1.22, 23.42, "path/to/picture/2"));
        when(movieService.getRandom()).thenReturn(movies);
        mockMvc.perform(get("/movie/random"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nameNative", is("Random Movie 1")))
                .andExpect(jsonPath("$[0].nameRussian", is("Случайный Фильм 1")))
                .andExpect(jsonPath("$[0].yearOfRelease", is(2001)))
                .andExpect(jsonPath("$[0].rating", is(1.21)))
                .andExpect(jsonPath("$[0].price", is(23.41)))
                .andExpect(jsonPath("$[0].picturePath", is("path/to/picture/1")));
        verify(movieService, times(1)).getRandom();
        verifyNoMoreInteractions(movieService);
    }

    @Test
    public void getMoviesByGenreTest() throws Exception {
        int id = 1;
        List<Movie> movies = Arrays.asList(
                new Movie(1, "Movie 1", "Фильм 1", 2001, 1.21, 23.41, "path/to/picture/1"),
                new Movie(2, "Movie 2", "Фильм 2", 2002, 1.22, 23.42, "path/to/picture/2"));
        when(movieService.getByGenre(id, null)).thenReturn(movies);
        mockMvc.perform(get("/movie/genre/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nameNative", is("Movie 1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].nameRussian", is("Фильм 2")));
        verify(movieService, times(1)).getByGenre(id, null);
        verifyNoMoreInteractions(movieService);
    }

    @Test
    public void getMoviesByIdTest() throws Exception {
        int id = 1;
        Movie movie = new Movie(1, "Movie 1", "Фильм 1", 2001, 1.21, 23.41, "path/to/picture/1");
        movie.setDescription("Description 1");
        movie.setCountries(getMockCountries());
        movie.setGenres(getMockGenres());
        movie.setReviews(getMockReviews());

        when(movieService.getById(id)).thenReturn(movie);
        mockMvc.perform(get("/movie/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(movie.getId())))
                .andExpect(jsonPath("$[0].nameNative", is(movie.getNameNative())))
                .andExpect(jsonPath("$[0].nameNative", is(movie.getNameRussian())))
                .andExpect(jsonPath("$[0].yearOfRelease", is(movie.getYearOfRelease())))
                .andExpect(jsonPath("$[0].description", is(movie.getDescription())))
                .andExpect(jsonPath("$[0].rating", is(movie.getRating())))
                .andExpect(jsonPath("$[0].price", is(movie.getPrice())))
                .andExpect(jsonPath("$[0].picturePath", is(movie.getPicturePath())))
                .andExpect(jsonPath("$[0].countries[0]", is(movie.getCountries().get(0))));
        verify(movieService, times(1)).getByGenre(id, null);
        verifyNoMoreInteractions(movieService);
    }

    @Test
    public void createMovieRequestParamTest() {
        // Sort by rating desc
        SortDirection ratingSorting = SortDirection.DESC;
        SortDirection priceSorting = null;
        MovieRequestParam movieRequestParam = movieController.createMovieRequestParam(ratingSorting, priceSorting);
        assertEquals("rating", movieRequestParam.getSortFieldName());
        assertEquals(SortDirection.DESC, movieRequestParam.getSortDirection());

        // Sort by price asc
        SortDirection ratingSorting2 = null;
        SortDirection priceSorting2 = SortDirection.ASC;
        MovieRequestParam movieRequestParam2 = movieController.createMovieRequestParam(ratingSorting2, priceSorting2);
        assertEquals("price", movieRequestParam2.getSortFieldName());
        assertEquals(SortDirection.ASC, movieRequestParam2.getSortDirection());

        // no order
        SortDirection ratingSorting3 = null;
        SortDirection priceSorting3 = null;
        MovieRequestParam movieRequestParam3 = movieController.createMovieRequestParam(ratingSorting3, priceSorting3);
        assertNull(movieRequestParam3);

    }

    @Test(expected = BadRequestParamException.class)
    public void createMovieRequestParamBadRequestParamExceptionTest() {
        // Sort by rating ASC
        SortDirection ratingSorting = SortDirection.ASC;
        SortDirection priceSorting = null;
        movieController.createMovieRequestParam(ratingSorting, priceSorting);
    }


    private List<Country> getMockCountries() {
        List<Country> countries = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            Country country = new Country();
            country.setId(i);
            country.setName("Name " + i);
            countries.add(country);
        }
        return countries;
    }

    private List<Genre> getMockGenres() {
        List<Genre> genres = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            genres.add(Genre.newBuilder()
                    .setId(i)
                    .setName("Name " + i)
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