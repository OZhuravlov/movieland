package com.study.movieland.dao.jdbc;

import com.study.movieland.dao.GenreDao;
import com.study.movieland.dao.jdbc.mapper.GenreRowMapper;
import com.study.movieland.entity.Genre;
import com.study.movieland.exception.NoDataFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcGenreDao implements GenreDao {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String GET_ALL_SQL = "SELECT id, name FROM genres";
    private static final String GET_BY_ID_SQL = "SELECT id, name FROM genres WHERE id = ?";
    private static final GenreRowMapper GENRE_ROW_MAPPER = new GenreRowMapper();
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> getAll() {
        logger.info("get All Genres");
        List<Genre> genres = jdbcTemplate.query(GET_ALL_SQL, GENRE_ROW_MAPPER);
        logger.debug("getAll: return List of {} Genre instance", genres);
        return genres;
    }

    @Override
    public Genre getById(int id) throws NoDataFoundException {
        try {
            logger.info("getting genre by id {}", id);
            Genre genre = jdbcTemplate.queryForObject(GET_BY_ID_SQL, GENRE_ROW_MAPPER, id);
            return genre;
        } catch (IncorrectResultSizeDataAccessException e) {
            String warnMessage = "Not such genre with id " + id;
            logger.warn(warnMessage);
            throw new NoDataFoundException(warnMessage, e);
        }
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
