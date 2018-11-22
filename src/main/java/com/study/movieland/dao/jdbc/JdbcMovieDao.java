package com.study.movieland.dao.jdbc;

import com.study.movieland.dao.MovieDao;
import com.study.movieland.dao.jdbc.mapper.MovieRowMapper;
import com.study.movieland.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcMovieDao implements MovieDao {

    @Value("${dao.movie.randomCount:3}")
    private int randomCount;

    private static final String GET_ALL_SQL =
            "SELECT id, name_native, name_russian, year_of_release, rating, price, picture_path FROM movies";
    private static final String GET_RANDOM_SQL =
            "SELECT id, name_native, name_russian, year_of_release, rating, price, picture_path FROM movies ORDER BY RANDOM()";
    private static final MovieRowMapper MOVIE_ROW_MAPPER = new MovieRowMapper();

    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Movie> getAll() {
        return jdbcTemplate.query(GET_ALL_SQL, MOVIE_ROW_MAPPER);
    }

    @Override
    public List<Movie> getRandom() {
        return jdbcTemplate.query(GET_RANDOM_SQL + " LIMIT " + randomCount, MOVIE_ROW_MAPPER);
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
