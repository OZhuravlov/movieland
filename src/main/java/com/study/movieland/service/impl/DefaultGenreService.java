package com.study.movieland.service.impl;

import com.study.movieland.dao.GenreDao;
import com.study.movieland.entity.Genre;
import com.study.movieland.service.GenreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultGenreService implements GenreService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private GenreDao genreDao;

    @Override
    public List<Genre> getAll() {
        logger.debug("Service: Get All Genres");
        long startTime = System.currentTimeMillis();
        List<Genre> genres = genreDao.getAll();
        logger.debug("Query took:{}", System.currentTimeMillis() - startTime);
        return genres;
    }

    @Autowired
    public void setGenreDao(GenreDao genreDao) {
        this.genreDao = genreDao;
    }
}
