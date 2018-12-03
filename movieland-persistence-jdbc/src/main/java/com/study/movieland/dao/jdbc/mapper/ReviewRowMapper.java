package com.study.movieland.dao.jdbc.mapper;

import com.study.movieland.entity.Review;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewRowMapper implements RowMapper<Review> {

    private final UserForReviewRowMapper userRowMapper = new UserForReviewRowMapper();

    @Override
    public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
        Review review = new Review();
        review.setId(rs.getInt("id"));
        review.setText(rs.getString("review_text"));
        review.setUser(userRowMapper.mapRow(rs, rowNum));
        return review;
    }
}