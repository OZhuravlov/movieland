package com.study.movieland.dao.jdbc;

import com.study.movieland.dao.MovieDao;
import com.study.movieland.dao.jdbc.mapper.MovieRowMapper;
import com.study.movieland.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcMovieDao implements MovieDao {

    private static final String GET_ALL_SQL =
            "SELECT id, name_native, name_russian, year_of_release, rating, price, picture_path FROM movies";
    private static final MovieRowMapper MOVIE_ROW_MAPPER = new MovieRowMapper();

    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Movie> getAll() {
        return jdbcTemplate.query(GET_ALL_SQL, MOVIE_ROW_MAPPER);
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
