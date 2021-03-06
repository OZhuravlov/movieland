package com.study.movieland.web.controller;

import com.study.movieland.entity.Country;
import com.study.movieland.entity.Genre;
import com.study.movieland.service.CountryService;
import com.study.movieland.service.GenreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/country")
public class CountryController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private CountryService countryService;

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public List<Country> getAllGenres() {
        logger.info("Get all countries");
        List<Country> countries = countryService.getAll();
        logger.debug("Returning {} countries", countries.size());
        return countries;
    }

    @Autowired
    public void setGenreService(CountryService countryService) {
        this.countryService = countryService;
    }
}
