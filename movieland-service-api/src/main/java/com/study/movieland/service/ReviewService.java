package com.study.movieland.service;

import com.study.movieland.entity.Movie;

public interface ReviewService {

    void enrichMovie(Movie movie);

    void add(int movieId, int userId, String text);
}

