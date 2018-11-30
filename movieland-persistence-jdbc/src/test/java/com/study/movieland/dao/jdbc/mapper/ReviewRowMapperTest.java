package com.study.movieland.dao.jdbc.mapper;

import com.study.movieland.entity.Review;
import com.study.movieland.entity.User;
import org.junit.Test;

import java.sql.ResultSet;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReviewRowMapperTest {

    @Test
    public void mapRowTest() throws Exception {
        User mockUser = new User();
        mockUser.setId(5);
        mockUser.setNickname("Mock User");
        ReviewRowMapper reviewRowMapper = new ReviewRowMapper();
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("review_text")).thenReturn("Review text");
        when(resultSet.getInt("user_id")).thenReturn(mockUser.getId());
        when(resultSet.getString("nickname")).thenReturn(mockUser.getNickname());

        // when
        Review review = reviewRowMapper.mapRow(resultSet, 1);

        // then
        assertEquals(1, review.getId());
        assertEquals("Review text", review.getText());
        assertThat(review.getUser(), is(mockUser));
    }
}