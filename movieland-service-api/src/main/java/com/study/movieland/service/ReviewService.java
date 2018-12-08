package com.study.movieland.service;

import com.study.movieland.entity.Movie;
import com.study.movieland.entity.Review;

public interface ReviewService {

    void enrichMovie(Movie movie);

    void add(int movieId, Review review);
}

