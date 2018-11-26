package com.study.movieland.dao;

import com.study.movieland.entity.Movie;
import com.study.movieland.entity.MovieRequestParam;

import java.util.List;

public interface MovieDao {

    List<Movie> getAll(MovieRequestParam movieRequestParam);

    List<Movie> getRandom();

    List<Movie> getByGenreId(int genreId, MovieRequestParam movieRequestParam);
}
