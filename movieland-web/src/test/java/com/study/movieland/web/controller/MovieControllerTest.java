package com.study.movieland.web.controller;

import com.study.movieland.entity.Movie;
import com.study.movieland.entity.MovieRequestParam;
import com.study.movieland.entity.SortDirection;
import com.study.movieland.exception.BadRequestParamException;
import com.study.movieland.service.MovieService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
    public void getMoviesByGenre_OKTest() throws Exception {
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

//    @Test
//    public void getMoviesByGenre_NoDataFoundExceptionTest() throws Exception {
//        int id = 0;
//        when(movieService.getByGenre(id, null)).thenThrow(new IllegalArgumentException("No such genre"));
//        mockMvc.perform(get("/movie/genre/" + id))
//                .andExpect(status().isNotFound());
//        verify(movieService, times(1)).getByGenre(id, null);
//        verifyNoMoreInteractions(movieService);
//    }

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

}