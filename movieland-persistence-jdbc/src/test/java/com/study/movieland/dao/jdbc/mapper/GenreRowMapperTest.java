package com.study.movieland.dao.jdbc.mapper;

import com.study.movieland.entity.Genre;
import org.junit.Test;

import java.sql.ResultSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GenreRowMapperTest {
    @Test
    public void testMapRow() throws Exception {
        GenreRowMapper genreRowMapper = new GenreRowMapper();
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("Genre name");

        // when
        Genre actualGenre = genreRowMapper.mapRow(resultSet, 1);

        // then
        assertEquals(1, actualGenre.getId());
        assertEquals("Genre name", actualGenre.getName());
    }
}