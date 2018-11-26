package com.study.movieland.dao.jdbc;

import com.study.movieland.dao.MovieDao;
import com.study.movieland.dao.jdbc.mapper.MovieRowMapper;
import com.study.movieland.entity.Movie;
import com.study.movieland.entity.OrderBy;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class JdbcMovieDao implements MovieDao {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String GET_ALL_SQL =
            "SELECT id, name_native, name_russian, year_of_release, rating, price, picture_path FROM movies";
    private static final String GET_RANDOM_SQL =
            "SELECT id, name_native, name_russian, year_of_release, rating, price, picture_path FROM movies ORDER BY RANDOM() LIMIT ?";
    private static final String GET_BY_GENRE_SQL =
            "SELECT m.id, m.name_native, m.name_russian, m.year_of_release, m.rating, m.price, m.picture_path" +
                    "  FROM movies m" +
                    "  JOIN movie_genres mg ON mg.movie_id = m.id" +
                    " WHERE mg.genre_id = ?";

    private static final MovieRowMapper MOVIE_ROW_MAPPER = new MovieRowMapper();

    @Autowired
    @Setter
    private JdbcTemplate jdbcTemplate;

    @Value("${dao.movie.randomCount:3}")
    private int randomCount;

    @Override
    public List<Movie> getAll(OrderBy orderBy) {
        String sql = GET_ALL_SQL;
        if (orderBy != null) {
            sql += " ORDER BY " + orderBy.getFieldName() + " " + orderBy.getSortDirection();
            logger.info("get All Movies order by {} {}", orderBy.getFieldName(), orderBy.getSortDirection());
        } else {
            logger.info("get All Movies");
        }
        List<Movie> movies = jdbcTemplate.query(sql, MOVIE_ROW_MAPPER);
        logger.debug("getAll: return List of {} Movie", movies.size());
        return movies;
    }

    @Override
    public List<Movie> getRandom() {
        logger.info("get Random Movies");
        List<Movie> movies = jdbcTemplate.query(GET_RANDOM_SQL, MOVIE_ROW_MAPPER, randomCount);
        logger.debug("getRandom: return List of {} Movie", movies.size());
        if (logger.isTraceEnabled()) {
            logger.trace(Arrays.toString(movies.toArray()));
        }
        return movies;
    }

    @Override
    public List<Movie> getByGenreId(int genreId, OrderBy orderBy) {
        String sql = GET_BY_GENRE_SQL;
        if (orderBy != null) {
            sql += " ORDER BY " + orderBy.getFieldName() + " " + orderBy.getSortDirection();
            logger.info("get Movies by Genre order by {} {}", orderBy.getFieldName(), orderBy.getSortDirection());
        } else {
            logger.info("get Movies by Genre");
        }
        List<Movie> movies = jdbcTemplate.query(sql, MOVIE_ROW_MAPPER, genreId);
        logger.debug("getByGenreId: return List of {} Movie", movies.size());
        if (logger.isTraceEnabled()) {
            logger.trace(Arrays.toString(movies.toArray()));
        }
        return movies;
    }

}
