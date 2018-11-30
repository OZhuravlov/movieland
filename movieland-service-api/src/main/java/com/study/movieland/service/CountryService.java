package com.study.movieland.service;

import com.study.movieland.entity.Movie;

import java.util.List;

public interface CountryService {

    void enrichMovie(Movie movie, List<Integer> countryIds);

}
