package com.study.movieland.dao.jdbc.mapper;

import com.study.movieland.entity.User;
import org.junit.Test;

import java.sql.ResultSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserForReviewRowMapperTest {

    @Test
    public void mapRowTest() throws Exception {
        UserForReviewRowMapper userForReviewRowMapper = new UserForReviewRowMapper();
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("user_id")).thenReturn(1);
        when(resultSet.getString("nickname")).thenReturn("User nickname");

        // when
        User user = userForReviewRowMapper.mapRow(resultSet, 1);

        // then
        assertEquals(1, user.getId());
        assertEquals("User nickname", user.getNickname());
    }
}