package com.study.movieland.dao;

import com.study.movieland.entity.Country;
import com.study.movieland.entity.Movie;

import java.util.List;

public interface CountryDao {

    Country getById(int id);

    List<Country> getByMovieId(int movieId);

    List<Country> getAll();

    void addReference(Movie movie);

    void editReference(Movie movie);
}
