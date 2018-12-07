package com.study.movieland.dao.jdbc.mapper;

import com.study.movieland.entity.Role;
import com.study.movieland.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setNickname(rs.getString("nickname"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setSole(rs.getString("sole"));
        user.setRole(Role.valueOf(rs.getString("role")));
        return user;
    }
}
