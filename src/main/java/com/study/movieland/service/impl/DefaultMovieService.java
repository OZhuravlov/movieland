package com.study.movieland.service.impl;

import com.study.movieland.dao.MovieDao;
import com.study.movieland.entity.Movie;
import com.study.movieland.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class DefaultMovieService implements MovieService {

    private MovieDao movieDao;
    private final static int RANDOM_MOVIE_AMOUNT = 3;

    @Override
    public List<Movie> getAll() {
        return movieDao.getAll();
    }

    @Override
    public List<Movie> getRandom() {
        List<Movie> movies = movieDao.getAll();
        List<Movie> randomMovies = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            int index = random.nextInt(movies.size());
            randomMovies.add(movies.get(index));
            movies.remove(index);
        }
        return randomMovies;
    }

    @Autowired
    public void setMovieDao(MovieDao movieDao) {
        this.movieDao = movieDao;
    }
}