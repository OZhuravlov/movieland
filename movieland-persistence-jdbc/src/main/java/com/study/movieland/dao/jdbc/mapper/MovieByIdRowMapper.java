package com.study.movieland.dao.jdbc.mapper;

import com.study.movieland.entity.Currency;
import com.study.movieland.entity.Movie;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieByIdRowMapper implements RowMapper<Movie> {

    private static final Currency DEFAULT_CURRENCY = Currency.UAH;

    @Override
    public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
        Movie movie = new Movie();
        movie.setId(rs.getInt("id"));
        movie.setNameNative(rs.getString("name_native"));
        movie.setNameRussian(rs.getString("name_russian"));
        movie.setYearOfRelease(rs.getInt("year_of_release"));
        movie.setRating(rs.getDouble("rating"));
        movie.setPrice(rs.getDouble("price"));
        movie.setCurrency(DEFAULT_CURRENCY);
        movie.setPicturePath(rs.getString("picture_path"));
        movie.setDescription(rs.getString("description"));
        return movie;
    }
}