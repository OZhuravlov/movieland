package com.study.movieland.service;

import com.study.movieland.entity.Country;
import com.study.movieland.entity.Movie;

import java.util.List;

public interface CountryService {

    void enrich(Movie movie);

    List<Country> getAll();

    void addReference(Movie movie);

    void editReference(Movie movie);
}
