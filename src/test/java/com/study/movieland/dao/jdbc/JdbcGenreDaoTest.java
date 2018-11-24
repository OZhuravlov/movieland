package com.study.movieland.dao.jdbc;

import com.study.movieland.dao.jdbc.mapper.MovieRowMapper;
import com.study.movieland.exception.NoDataFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

@RunWith(MockitoJUnitRunner.class)
public class JdbcGenreDaoTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private JdbcGenreDao jdbcGenreDao = new JdbcGenreDao();

    @Test(expected = NoDataFoundException.class)
    public void testGetByIdNoDataFoundException() {
        Mockito.when(
                jdbcTemplate.queryForObject(
                        Mockito.anyString(),
                        Mockito.any(MovieRowMapper.class),
                        Mockito.anyVararg()))
                .thenThrow(new IncorrectResultSizeDataAccessException(1));
        jdbcGenreDao.getById(0);
    }
}