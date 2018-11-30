package com.study.movieland.dao.jdbc;

import com.study.movieland.dao.jdbc.mapper.MovieRowMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

@RunWith(MockitoJUnitRunner.class)
public class JdbcMovieDaoTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private JdbcMovieDao jdbcMovieDao = new JdbcMovieDao();

    @Test(expected = IllegalArgumentException.class)
    public void testGetByIdNoDataFoundException() {
        Mockito.when(
                jdbcTemplate.queryForObject(
                        Mockito.anyString(),
                        Mockito.any(MovieRowMapper.class),
                        Mockito.anyVararg()))
                .thenThrow(new IncorrectResultSizeDataAccessException(1));
        jdbcMovieDao.getById(0);
    }
}