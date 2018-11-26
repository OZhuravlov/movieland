package com.study.movieland.service;

import com.study.movieland.entity.Genre;

import java.util.Collection;
import java.util.List;

public interface GenreService {

    Collection<Genre> getAll();

    Genre getById(int id);

}
