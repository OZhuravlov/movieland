package com.study.movieland.service;

import com.study.movieland.entity.Movie;

import java.util.Optional;

public interface CacheService {

    Optional<Movie> getMovie(Integer id);

    void putMovie(Movie movie);

    void removeMovie(int id);

    boolean existsMovie(int id);
}
