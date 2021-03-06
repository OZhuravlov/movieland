package com.study.movieland.dao.jdbc;

import com.study.movieland.dao.CountryDao;
import com.study.movieland.dao.jdbc.mapper.CountryRowMapper;
import com.study.movieland.entity.Country;
import com.study.movieland.entity.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcCountryDao implements CountryDao {

    private static final String GET_BY_ID_SQL = "SELECT id, name FROM countries WHERE id = ?";
    private static final String GET_BY_MOVIE_ID_SQL = "SELECT c.id, c.name " +
            "  FROM countries c JOIN movie_countries mc ON mc.country_id = c.id " +
            " WHERE mc.movie_id = ?";
    private static final String GET_ALL_SQL = "SELECT id, name FROM countries";
    private static final String REMOVE_MOVIE_COUNTRIES_SQL = "DELETE FROM movie_countries WHERE movie_id = ?";
    private static final String ADD_MOVIE_COUNTRIES_SQL = "INSERT INTO movie_countries (movie_id, country_id) " +
            " VALUES(:movieId, :countryId)";

    private static final CountryRowMapper COUNTRY_ROW_MAPPER = new CountryRowMapper();

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

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

    @Override
    public List<Country> getAll() {
        logger.info("getting all countries");
        List<Country> countries = jdbcTemplate.query(GET_ALL_SQL, COUNTRY_ROW_MAPPER);
        logger.trace("get countries: {}", countries);
        return new ArrayList(countries);
    }

    @Override
    public void addReference(Movie movie) {
        logger.info("add countries for movie");
        List<MapSqlParameterSource> batchValues = getBatchValue(movie);
        namedParameterJdbcTemplate.batchUpdate(ADD_MOVIE_COUNTRIES_SQL,
                batchValues.toArray(new MapSqlParameterSource[movie.getCountries().size()]));
    }

    @Override
    public void editReference(Movie movie) {
        logger.info("edit countries for movie");
        List<MapSqlParameterSource> batchValues = getBatchValue(movie);
        jdbcTemplate.update(REMOVE_MOVIE_COUNTRIES_SQL, movie.getId());
        namedParameterJdbcTemplate.batchUpdate(ADD_MOVIE_COUNTRIES_SQL,
                batchValues.toArray(new MapSqlParameterSource[movie.getCountries().size()]));
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private List<MapSqlParameterSource> getBatchValue(Movie movie){
        List<MapSqlParameterSource> batchValues = new ArrayList<>();
        for (Country country : movie.getCountries()) {
            batchValues.add(
                    new MapSqlParameterSource()
                            .addValue("movieId", movie.getId())
                            .addValue("countryId", country.getId())
            );
        }
        return batchValues;
    }
}
