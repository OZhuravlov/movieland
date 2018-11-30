package com.study.movieland.dao.jdbc;

import com.study.movieland.dao.CountryDao;
import com.study.movieland.dao.jdbc.mapper.CountryRowMapper;
import com.study.movieland.entity.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcCountryDao implements CountryDao {

    private static final String GET_BY_ID_SQL = "SELECT id, name FROM countries WHERE id = ?";
    private static final String GET_BY_MOVIE_ID_SQL = "SELECT c.id, c.name " +
            "  FROM countries c JOIN movie_countries mc ON mc.movie_id = c.id " +
            " WHERE mc.movie_id = ?";
    private static final CountryRowMapper COUNTRY_ROW_MAPPER = new CountryRowMapper();

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private JdbcTemplate jdbcTemplate;

    @Override
    public Country getById(int id) {
        logger.info("getting country by id {}", id);
        Country country = jdbcTemplate.queryForObject(GET_BY_ID_SQL, COUNTRY_ROW_MAPPER, id);
        return country;
    }

    @Override
    public List<Country> getByMovieId(int movieId) {
        logger.info("getting countries by movieId {}", movieId);
        List<Country> countries = jdbcTemplate.query(GET_BY_MOVIE_ID_SQL, COUNTRY_ROW_MAPPER, movieId);
        logger.trace("get countries: {}", countries);
        return countries;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
