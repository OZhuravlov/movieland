package com.study.movieland.dao.jdbc;

import com.study.movieland.dao.GenreDao;
import com.study.movieland.dao.jdbc.mapper.GenreRowMapper;
import com.study.movieland.entity.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.security.InvalidParameterException;
import java.util.List;

@Repository
public class JdbcGenreDao implements GenreDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcGenreDao.class);

    private static final String GET_ALL_SQL = "SELECT id, name FROM genres";
    private static final String GET_BY_ID_SQL = "SELECT id, name FROM genres WHERE id = ?";
    private static final GenreRowMapper GENRE_ROW_MAPPER = new GenreRowMapper();
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> getAll() {
        logger.debug("Dao get All Genres");
        return jdbcTemplate.query(GET_ALL_SQL, GENRE_ROW_MAPPER);
    }

    @Override
    public Genre getById(int id) {
        logger.debug("Dao getting Genre by id");
        try {
            Genre genre = jdbcTemplate.queryForObject(GET_BY_ID_SQL, GENRE_ROW_MAPPER, id);
            return genre;
        } catch(IncorrectResultSizeDataAccessException e){
            String errorMessage = "Not such genre with id " + id;
            logger.error(errorMessage);
            throw new InvalidParameterException(errorMessage);
        }
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
