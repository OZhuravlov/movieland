package com.study.movieland.dao.jdbc.mapper;

import com.study.movieland.entity.Movie;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieRowMapper implements RowMapper<Movie> {
    // id, name_native, name_russian, year_of_release, country, description, rating, price, picture_path
    @Override
    public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
        Movie movie = new Movie();
        movie.setId(rs.getInt("id"));
        movie.setNameNative(rs.getString("name_native"));
        movie.setNameRussian(rs.getString("name_russian"));
        movie.setYearOfRelease(rs.getString("year_of_release"));
        movie.setRating(rs.getDouble("rating"));
        movie.setPrice(rs.getDouble("price"));
        return movie;
    }
}