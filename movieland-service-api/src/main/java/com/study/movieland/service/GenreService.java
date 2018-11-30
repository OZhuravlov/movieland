package com.study.movieland.service;

import com.study.movieland.entity.Genre;
import com.study.movieland.entity.Movie;

import java.util.List;

public interface GenreService {

    List<Genre> getAll();

    Genre getById(int id);

    void enrichMovie(Movie movie, List<Integer> genreIds);

}
