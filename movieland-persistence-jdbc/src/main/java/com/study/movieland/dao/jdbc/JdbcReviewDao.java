package com.study.movieland.dao.jdbc;

import com.study.movieland.dao.ReviewDao;
import com.study.movieland.dao.jdbc.mapper.ReviewRowMapper;
import com.study.movieland.entity.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcReviewDao implements ReviewDao {

    private static final String GET_BY_MOVIE_ID_SQL = "SELECT r.id, r.review_text, r.user_id, u.nickname " +
            "  FROM reviews r JOIN users u ON r.user_id = u.id " +
            " WHERE r.movie_id = ?";
    private static final String ADD_REVIEW_SQL = "INSERT INTO reviews (movie_id, user_id, review_text) VALUES(?, ?, ?)";

    private static final ReviewRowMapper REVIEW_ROW_MAPPER = new ReviewRowMapper();
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Review> getByMovieId(int movieId) {
        logger.info("getting reviews by movieId {}", movieId);
        List<Review> reviews = jdbcTemplate.query(GET_BY_MOVIE_ID_SQL, REVIEW_ROW_MAPPER, movieId);
        logger.trace("get reviews: {}", reviews);
        return reviews;
    }

    @Override
    public void add(int movieId, int userId, String text) {
        logger.info("add review for movieId {} by {}", movieId, userId);
        try {
            jdbcTemplate.update(ADD_REVIEW_SQL, movieId, userId, text);
        } catch (Exception e) {
            logger.warn("Error:", e);
        }
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
