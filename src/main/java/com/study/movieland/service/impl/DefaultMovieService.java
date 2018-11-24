package com.study.movieland.service.impl;

import com.study.movieland.dao.MovieDao;
import com.study.movieland.entity.Genre;
import com.study.movieland.entity.Movie;
import com.study.movieland.service.GenreService;
import com.study.movieland.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;

@Service
public class DefaultMovieService implements MovieService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultMovieService.class);

    private MovieDao movieDao;
    private GenreService genreService;

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

    @Override
    public List<Movie> getByGenre(int genreId) {
        logger.debug("Service: getting Genre for id {}", genreId);
        Genre genre = genreService.getById(genreId);
        logger.debug("Service: getting Movies by Genre");
        return movieDao.getByGenreId(genre.getId());
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