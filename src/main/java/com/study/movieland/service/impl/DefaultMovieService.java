package com.study.movieland.service.impl;

import com.study.movieland.dao.MovieDao;
import com.study.movieland.entity.Movie;
import com.study.movieland.service.MovieService;
import com.study.movieland.web.controller.MovieController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultMovieService implements MovieService {

    private MovieDao movieDao;
    private static final Logger logger = LoggerFactory.getLogger(DefaultMovieService.class);

    @Override
    public List<Movie> getAll() {
        logger.debug("Service: Get All Movies");
        return movieDao.getAll();
    }

    @Override
    public List<Movie> getRandom() {
        logger.debug("Service: Get Debug Movies");
        return movieDao.getRandom();
    }

    @Autowired
    public void setMovieDao(MovieDao movieDao) {
        this.movieDao = movieDao;
    }
}