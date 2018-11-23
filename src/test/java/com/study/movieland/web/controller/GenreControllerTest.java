package com.study.movieland.web.controller;

import com.study.movieland.entity.Genre;
import com.study.movieland.service.impl.DefaultGenreService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class GenreControllerTest {

    @Mock
    DefaultGenreService genreService;

    @InjectMocks
    GenreController genreController = new GenreController();

    private MockMvc mockMvc;

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(genreController)
                .build();
    }

    @Test
    public void getAllGenres() throws Exception {
        List<Genre> genres = Arrays.asList(
                new Genre(1, "Genre 1"),
                new Genre(2, "Genre 2"));
        when(genreService.getAll()).thenReturn(genres);
        mockMvc.perform(get("/genre"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Genre 1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Genre 2")));
        verify(genreService, times(1)).getAll();
        verifyNoMoreInteractions(genreService);
    }

}