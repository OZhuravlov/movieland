package com.study.movieland.web.controller;

import com.study.movieland.entity.Movie;
import com.study.movieland.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MovieController {

    private MovieService movieService;

    @RequestMapping(value = {"/v1/movie"}, method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    List<Movie> getAllMovies() {
        return movieService.getAll();
    }

    @Autowired
    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }
}
