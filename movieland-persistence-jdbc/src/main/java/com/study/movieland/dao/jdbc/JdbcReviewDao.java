package com.study.movieland.dao.jdbc;

import com.study.movieland.dao.ReviewDao;
import com.study.movieland.dao.jdbc.mapper.ReviewRowMapper;
import com.study.movieland.entity.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcReviewDao implements ReviewDao {

    private static final String GET_BY_MOVIE_ID_SQL = "SELECT r.id, r.review_text, r.user_id, u.nickname " +
            "  FROM reviews r JOIN users u ON r.user_id = u.id " +
            " WHERE r.movie_id = ?";
    private static final String ADD_REVIEW_SQL = "INSERT INTO reviews (movie_id, user_id, review_text) VALUES(:movie_id, :user_id, :text)";

    private static final ReviewRowMapper REVIEW_ROW_MAPPER = new ReviewRowMapper();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Review> getByMovieId(int movieId) {
        logger.info("getting reviews by movieId {}", movieId);
        List<Review> reviews = jdbcTemplate.query(GET_BY_MOVIE_ID_SQL, REVIEW_ROW_MAPPER, movieId);
        logger.trace("get reviews: {}", reviews);
        return reviews;
    }

    @Override
    public void add(int movieId, Review review) {
        logger.info("add review for movieId {}: {}", movieId, review);
        MapSqlParameterSource queryParams = new MapSqlParameterSource();
        queryParams.addValue("movie_id", movieId);
        queryParams.addValue("user_id", review.getUser().getId());
        queryParams.addValue("text", review.getText());
        namedParameterJdbcTemplate.update(ADD_REVIEW_SQL, queryParams);
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
