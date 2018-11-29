package com.study.movieland.service.impl;

import com.study.movieland.dao.MovieDao;
import com.study.movieland.entity.Genre;
import com.study.movieland.entity.Movie;
import com.study.movieland.entity.MovieRequestParam;
import com.study.movieland.service.GenreService;
import com.study.movieland.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultMovieService implements MovieService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private MovieDao movieDao;
    private GenreService genreService;

    @Override
    public List<Movie> getAll(MovieRequestParam movieRequestParam) {
        logger.info("get All Movies");
        long startTime = System.currentTimeMillis();
        List<Movie> movies = movieDao.getAll(movieRequestParam);
        logger.debug("Query took:{}", System.currentTimeMillis() - startTime);
        return movies;
    }

    @Override
    public List<Movie> getRandom() {
        logger.info("get Random Movies");
        long startTime = System.currentTimeMillis();
        List<Movie> movies = movieDao.getRandom();
        logger.debug("Query took:{}", System.currentTimeMillis() - startTime);
        return movies;
    }

    @Override
    public List<Movie> getByGenre(int genreId, MovieRequestParam movieRequestParam) {
        logger.info("get Movies by Genre", genreId);
        logger.info("check Genre for id {}", genreId);
        Genre genre = genreService.getById(genreId);
        long startTime = System.currentTimeMillis();
        logger.debug("Query took:{}", System.currentTimeMillis() - startTime);
        return movieDao.getByGenreId(genre.getId(), movieRequestParam);
    }

    @Autowired
    public void setMovieDao(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Autowired
    public void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }
}