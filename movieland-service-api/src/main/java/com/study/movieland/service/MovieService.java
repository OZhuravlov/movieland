package com.study.movieland.service;

import com.study.movieland.data.MovieRequestParam;
import com.study.movieland.entity.Movie;

import java.util.List;

public interface MovieService {

    List<Movie> getAll(MovieRequestParam movieRequestParam);

    List<Movie> getRandom();

    List<Movie> getByGenre(int genreId, MovieRequestParam movieRequestParam);

    Movie getById(int id, MovieRequestParam movieRequestParam);

}
