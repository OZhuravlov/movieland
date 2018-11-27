package com.study.movieland.dao.jdbc.mapper;

import com.study.movieland.entity.Genre;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreRowMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        Genre genre = Genre.newBuilder()
                .setId(rs.getInt("id"))
                .setName(rs.getString("name"))
                .build();
        return genre;
    }
}