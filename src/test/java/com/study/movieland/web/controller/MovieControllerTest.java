package com.study.movieland.web.controller;

import com.study.movieland.entity.Movie;
import com.study.movieland.service.impl.DefaultMovieService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringJUnitWebConfig(locations = "classpath:test-servlet-context.xml")
public class MovieControllerTest {

    @Mock
    DefaultMovieService movieService;

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
    public void getMovies() throws Exception {
        List<Movie> movies = Arrays.asList(
                new Movie(1, "Movie 1", "Фильм 1", 2001, 1.21, 23.41, "path/to/picture/1"),
                new Movie(2, "Movie 2", "Фильм 2", 2002, 1.22, 23.42, "path/to/picture/2"));
        when(movieService.getAll()).thenReturn(movies);
        mockMvc.perform(get("/movie"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nameNative", is("Movie 1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].nameRussian", is("Фильм 2")));
        verify(movieService, times(1)).getAll();
        verifyNoMoreInteractions(movieService);
    }
}