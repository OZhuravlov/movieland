package com.study.movieland.service.impl;

import com.study.movieland.dao.MovieDao;
import com.study.movieland.entity.Movie;
import com.study.movieland.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultMovieService implements MovieService {

    private MovieDao movieDao;

    @Override
    public List<Movie> getAll() {
        return movieDao.getAll();
    }

    @Autowired
    public void setUserDao(MovieDao movieDao) {
        this.movieDao = movieDao;
    }
}