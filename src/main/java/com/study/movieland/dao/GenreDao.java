package com.study.movieland.dao;

import com.study.movieland.entity.Genre;

import java.util.Collection;
import java.util.List;

public interface GenreDao {

    Collection<Genre> getAll();

    Genre getById(int id);
}
