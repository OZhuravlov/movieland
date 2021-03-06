package com.study.movieland.dao;

import com.study.movieland.data.MovieRequestParam;
import com.study.movieland.entity.Movie;

import java.util.List;

public interface MovieDao {

    List<Movie> getAll(MovieRequestParam movieRequestParam);

    List<Movie> getRandom();

    List<Movie> getByGenreId(int genreId, MovieRequestParam movieRequestParam);

    Movie getById(int id);

    void add(Movie movie);

    void edit(Movie movie);
}
