package com.study.movieland.service;

import com.study.movieland.entity.Movie;
import com.study.movieland.entity.OrderBy;
import com.study.movieland.entity.SortDirection;

import java.util.List;

public interface MovieService {

    List<Movie> getAll(OrderBy orderBy);

    List<Movie> getRandom();

    List<Movie> getByGenre(int genreId, OrderBy orderBy);

}
