package com.study.movieland.dao;

import com.study.movieland.entity.Genre;
import com.study.movieland.entity.Movie;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public interface GenreDao {

    List<Genre> getAll();

    Genre getById(int id);

    List<Genre> getByMovieId(int movieId);

    default void addReference(Movie movie) {
        throw new NotImplementedException();
    }

    default void editReference(Movie movie){
        throw new NotImplementedException();
    }
}
