package com.study.movieland.service.impl;

import com.study.movieland.dao.MovieDao;
import com.study.movieland.entity.Movie;
import com.study.movieland.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultMovieService implements MovieService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultMovieService.class);

    private MovieDao movieDao;

    @Override
    public List<Movie> getAll() {
        logger.debug("Service: Get All Movies");
        return movieDao.getAll();
    }

    @Override
    public List<Movie> getRandom() {
        logger.debug("Service: Get Random Movies");
        return movieDao.getRandom();
    }

    @Autowired
    public void setMovieDao(MovieDao movieDao) {
        this.movieDao = movieDao;
    }
}