package com.study.movieland.dao;

import com.study.movieland.entity.Review;

import java.util.List;

public interface ReviewDao {
    List<Review> getByMovieId(int id);

    void add(int movieId, int userId, String text);
}
