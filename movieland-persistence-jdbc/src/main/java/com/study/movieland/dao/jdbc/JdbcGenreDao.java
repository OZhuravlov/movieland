package com.study.movieland.dao.jdbc;

import com.study.movieland.dao.GenreDao;
import com.study.movieland.dao.jdbc.mapper.GenreRowMapper;
import com.study.movieland.entity.Genre;
import com.study.movieland.entity.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcGenreDao implements GenreDao {

    private static final String GET_ALL_SQL = "SELECT id, name FROM genres";
    private static final String GET_BY_ID_SQL = "SELECT id, name FROM genres WHERE id = ?";
    private static final String GET_BY_MOVIE_ID_SQL = "SELECT g.id, g.name " +
            "  FROM genres g JOIN movie_genres mg ON mg.movie_id = g.id " +
            " WHERE mg.movie_id = ?";
    private static final String REMOVE_MOVIE_GENRES_SQL = "DELETE FROM movie_genres WHERE movie_id = :movieId";
    private static final String ADD_MOVIE_GENRES_SQL = "INSERT INTO movie_genres (movie_id, genre_id) " +
            " VALUES(:movieId, :genreId)";
    private static final GenreRowMapper GENRE_ROW_MAPPER = new GenreRowMapper();

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Genre> getAll() {
        logger.info("get All Genres");
        List<Genre> genres = jdbcTemplate.query(GET_ALL_SQL, GENRE_ROW_MAPPER);
        logger.debug("getAll: return List of {} Genre instance", genres);
        return new ArrayList<>(genres);
    }

    @Override
    public Genre getById(int id) {
        logger.info("getting genre by id {}", id);
        Genre genre = jdbcTemplate.queryForObject(GET_BY_ID_SQL, GENRE_ROW_MAPPER, id);
        return genre;
    }

    @Override
    public List<Genre> getByMovieId(int movieId) {
        logger.info("getting genres by movieId {}", movieId);
        List<Genre> genres = jdbcTemplate.query(GET_BY_MOVIE_ID_SQL, GENRE_ROW_MAPPER, movieId);
        logger.trace("get genres: {}", genres);
        return genres;
    }

    @Override
    public void addReference(Movie movie) {
        logger.info("add genres for movie");
        List<MapSqlParameterSource> batchValues = getBatchValue(movie);
        namedParameterJdbcTemplate.batchUpdate(ADD_MOVIE_GENRES_SQL,
                batchValues.toArray(new MapSqlParameterSource[movie.getGenres().size()]));
    }

    @Override
    public void editReference(Movie movie) {
        logger.info("edit genres for movie");
        List<MapSqlParameterSource> batchValues = getBatchValue(movie);
        jdbcTemplate.update(REMOVE_MOVIE_GENRES_SQL, movie.getId());
        namedParameterJdbcTemplate.batchUpdate(ADD_MOVIE_GENRES_SQL,
                batchValues.toArray(new MapSqlParameterSource[movie.getGenres().size()]));
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private List<MapSqlParameterSource> getBatchValue(Movie movie) {
        List<MapSqlParameterSource> batchValues = new ArrayList<>();
        for (Genre genre : movie.getGenres()) {
            batchValues.add(
                    new MapSqlParameterSource()
                            .addValue("movieId", movie.getId())
                            .addValue("genreId", genre.getId())
            );
        }
        return batchValues;
    }
}
