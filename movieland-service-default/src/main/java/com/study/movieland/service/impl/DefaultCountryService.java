package com.study.movieland.service.impl;

import com.study.movieland.dao.CountryDao;
import com.study.movieland.entity.Country;
import com.study.movieland.entity.Movie;
import com.study.movieland.service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultCountryService implements CountryService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private CountryDao countryDao;

    @Override
    public void enrich(Movie movie) {
        logger.info("Enrich Movie with countries");
        List<Country> countries = countryDao.getByMovieId(movie.getId());
        if (!Thread.currentThread().isInterrupted()) {
            logger.debug("Enrich Movie id {} with countries: {}", movie.getId(), countries);
            movie.setCountries(countries);
        } else {
            logger.warn("Enrich Movie id {}. Thread was interrupted", movie.getId());
        }
    }


    @Override
    public List<Country> getAll() {
        logger.info("Get all countries");
        List<Country> countries = countryDao.getAll();
        logger.trace("Get all countries: {}", countries);
        return countries;
    }

    @Override
    public void addReference(Movie movie) {
        countryDao.addReference(movie);
    }

    @Override
    public void editReference(Movie movie) {
        countryDao.editReference(movie);
    }

    @Autowired
    public void setCountryDao(CountryDao countryDao) {
        this.countryDao = countryDao;
    }
}
