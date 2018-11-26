package com.study.movieland.dao;

import com.study.movieland.entity.Movie;
import com.study.movieland.entity.OrderBy;

import java.util.List;

public interface MovieDao {

    List<Movie> getAll(OrderBy orderBy);

    List<Movie> getRandom();

    List<Movie> getByGenreId(int genreId, OrderBy orderBy);
}
