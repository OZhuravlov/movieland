package com.study.movieland.service.impl;

import com.study.movieland.dao.GenreDao;
import com.study.movieland.entity.Genre;
import com.study.movieland.entity.Movie;
import com.study.movieland.service.GenreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultGenreService implements GenreService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private GenreDao genreDao;

    @Override
    public List<Genre> getAll() {
        logger.debug("Get All Genres");
        long startTime = System.currentTimeMillis();
        List<Genre> genres = genreDao.getAll();
        logger.debug("Query took:{}", System.currentTimeMillis() - startTime);
        return genres;
    }

    @Override
    public Genre getById(int id) {
        logger.debug("Get Genre by Id");
        return genreDao.getById(id);
    }

    @Override
    public void enrichMovie(Movie movie) {
        logger.debug("Enrich Movie with genres");
        List<Genre> genres = genreDao.getByMovieId(movie.getId());
        if (!Thread.currentThread().isInterrupted()) {
            movie.setGenres(genres);
            logger.trace("Enrich Movie id {} with genres: {}", movie.getId(), genres);
        } else {
            logger.warn("Enrich Movie id {}. Thread was interrupted", movie.getId());
        }

    }

    @Override
    public void addReference(Movie movie) {
        genreDao.addReference(movie);
    }

    @Override
    public void editReference(Movie movie) {
        genreDao.editReference(movie);
    }

    @Autowired
    @Qualifier("cacheGenreDao")
    public void setGenreDao(GenreDao genreDao) {
        this.genreDao = genreDao;
    }
}
