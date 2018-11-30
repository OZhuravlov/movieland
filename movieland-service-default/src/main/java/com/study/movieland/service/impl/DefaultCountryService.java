package com.study.movieland.service.impl;

import com.study.movieland.dao.CountryDao;
import com.study.movieland.entity.Country;
import com.study.movieland.entity.Movie;
import com.study.movieland.service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultCountryService implements CountryService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private CountryDao countryDao;

    @Override
    public void enrichMovie(Movie movie, List<Integer> countryIds) {
        if (countryIds == null) {
            return;
        }
        logger.debug("Enrich Movie with countries");
        List<Country> countries = new ArrayList<>();
        for (Integer countryId : countryIds) {
            countries.add(countryDao.getById(countryId));
        }
        logger.trace("Enrich Movie id {} with countries: {}", movie.getId(), countries);
        movie.setCountries(countries);
    }

    @Autowired
    public void setCountryDao(CountryDao countryDao) {
        this.countryDao = countryDao;
    }
}
