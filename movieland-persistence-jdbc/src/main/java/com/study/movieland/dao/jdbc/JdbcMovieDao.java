package com.study.movieland.dao.jdbc;

import com.study.movieland.dao.MovieDao;
import com.study.movieland.dao.jdbc.mapper.MovieByIdRowMapper;
import com.study.movieland.dao.jdbc.mapper.MovieRowMapper;
import com.study.movieland.dao.jdbc.util.DaoUtils;
import com.study.movieland.data.MovieRequestParam;
import com.study.movieland.entity.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcMovieDao implements MovieDao {

    private static final String GET_ALL_SQL =
            "SELECT id, name_native, name_russian, year_of_release, rating, price, picture_path FROM movies";
    private static final String GET_RANDOM_SQL =
            "SELECT id, name_native, name_russian, year_of_release, rating, price, picture_path FROM movies ORDER BY RANDOM() LIMIT ?";
    private static final String GET_BY_GENRE_SQL =
            "SELECT m.id, m.name_native, m.name_russian, m.year_of_release, m.rating, m.price, m.picture_path" +
                    "  FROM movies m" +
                    "  JOIN movie_genres mg ON mg.movie_id = m.id" +
                    " WHERE mg.genre_id = ?";
    private static final String GET_BY_ID_SQL =
            "SELECT id, name_native, name_russian, year_of_release, description, rating, price, picture_path" +
                    "  FROM movies" +
                    " WHERE id = ?";

    private static final String ADD_SQL = "INSERT INTO movies (name_russian, name_native, year_of_release, description, price, picture_path)" +
            " VALUES(:nameRussian, :nameNative, :yearOfRelease, :description, :price, :picturePath)";
    private static final String EDIT_SQL = "UPDATE movies SET name_russian = :nameRussian, name_native = :nameNative, picture_path = :picturePath WHERE id = :movie_id";

    private static final MovieRowMapper MOVIE_ROW_MAPPER = new MovieRowMapper();
    private static final MovieByIdRowMapper MOVIE_BY_ID_ROW_MAPPER = new MovieByIdRowMapper();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private int randomCount;

    @Override
    public List<Movie> getAll(MovieRequestParam movieRequestParam) {
        logger.info("get All Movies");
        String sql = DaoUtils.addOptionalRequestParamsToQuery(GET_ALL_SQL, movieRequestParam);
        logger.debug("get All Movies query {}", sql);
        List<Movie> movies = jdbcTemplate.query(sql, MOVIE_ROW_MAPPER);
        logger.trace("getAll: return List of movies {}", movies);
        return movies;
    }

    @Override
    public List<Movie> getRandom() {
        logger.info("get Random Movies");
        List<Movie> movies = jdbcTemplate.query(GET_RANDOM_SQL, MOVIE_ROW_MAPPER, randomCount);
        logger.trace("getRandom: return List of movies {}", movies);
        return movies;
    }

    @Override
    public List<Movie> getByGenreId(int genreId, MovieRequestParam movieRequestParam) {
        logger.info("get Movies by genreId {}", genreId);
        String sql = DaoUtils.addOptionalRequestParamsToQuery(GET_BY_GENRE_SQL, movieRequestParam);
        logger.debug("get Movies by Genre query {}", sql);
        List<Movie> movies = jdbcTemplate.query(sql, MOVIE_ROW_MAPPER, genreId);
        logger.trace("getAll: return List of movies for genreId {}: {}", genreId, movies);
        return movies;
    }

    @Override
    public Movie getById(int id) {
        logger.info("get Movies by Id {}", id);
        Movie movie = jdbcTemplate.queryForObject(GET_BY_ID_SQL, MOVIE_BY_ID_ROW_MAPPER, id);
        logger.trace("getAll: return List of movies for genreId {}: {}", id, movie);
        return movie;
    }

    @Override
    public void add(Movie movie) {
        logger.info("add new movie: {}", movie);
        MapSqlParameterSource queryParams = new MapSqlParameterSource()
                .addValue("nameRussian", movie.getNameRussian())
                .addValue("nameNative", movie.getNameNative())
                .addValue("yearOfRelease", movie.getYearOfRelease())
                .addValue("description", movie.getDescription())
                .addValue("price", movie.getPrice())
                .addValue("picturePath", movie.getPicturePath());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(ADD_SQL, queryParams, keyHolder, new String[] {"id"});
        movie.setId((int) keyHolder.getKey());
    }

    @Override
    public void edit(Movie movie) {
        logger.info("edit movie: {}", movie);
        MapSqlParameterSource queryParams = new MapSqlParameterSource()
                .addValue("movie_id", movie.getId())
                .addValue("nameRussian", movie.getNameRussian())
                .addValue("nameNative", movie.getNameNative())
                .addValue("picturePath", movie.getPicturePath());
        namedParameterJdbcTemplate.update(EDIT_SQL, queryParams);
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Value("${dao.movie.randomCount:3}")
    public void setRandomCount(int randomCount) {
        this.randomCount = randomCount;
    }
}
