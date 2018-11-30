package com.study.movieland.dao.jdbc.mapper;

import com.study.movieland.entity.Review;
import com.study.movieland.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewRowMapper implements RowMapper<Review> {
    @Override
    public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
        Review review = new Review();
        review.setId(rs.getInt("id"));
        review.setText(rs.getString("review_text"));
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setNickname(rs.getString("nickname"));
        review.setUser(user);
        return review;
    }
}