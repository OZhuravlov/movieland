package com.study.movieland.dao.jdbc.mapper;

import com.study.movieland.entity.Movie;
import org.junit.Test;

import java.sql.ResultSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MovieByIdRowMapperTest {

    @Test
    public void mapRowTest() throws Exception {
        MovieByIdRowMapper movieByIdRowMapper = new MovieByIdRowMapper();
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("name_native")).thenReturn("Native name");
        when(resultSet.getString("name_russian")).thenReturn("Русское название");
        when(resultSet.getInt("year_of_release")).thenReturn(2000);
        when(resultSet.getString("description")).thenReturn("Film description");
        when(resultSet.getDouble("rating")).thenReturn(4.56);
        when(resultSet.getDouble("price")).thenReturn(24.50);
        when(resultSet.getString("picture_path")).thenReturn("http://path/to/picture");

        // when
        Movie actualMovie = movieByIdRowMapper.mapRow(resultSet, 1);

        // then
        assertEquals(1, actualMovie.getId());
        assertEquals("Native name", actualMovie.getNameNative());
        assertEquals("Русское название", actualMovie.getNameRussian());
        assertEquals(2000, actualMovie.getYearOfRelease());
        assertEquals("Film description", actualMovie.getDescription());
        assertEquals(4.56, actualMovie.getRating(), 0.0001);
        assertEquals(24.50, actualMovie.getPrice(), 0.0001);
        assertEquals("http://path/to/picture", actualMovie.getPicturePath());
    }
}