package com.study.movieland.service;

import com.study.movieland.entity.Movie;

import java.util.List;

public interface MovieService {

    List<Movie> getAll();

    List<Movie> getRandom();

    List<Movie> getByGenre(int genreId);

}
