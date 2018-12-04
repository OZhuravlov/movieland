package com.study.movieland.dao.jdbc;

import com.study.movieland.dao.UserDao;
import com.study.movieland.dao.jdbc.mapper.UserRowMapper;
import com.study.movieland.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcUserDao implements UserDao {

    private static final String GET_BY_EMAIL_SQL = "SELECT id, name FROM users WHERE email = ?";
    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private JdbcTemplate jdbcTemplate;

    @Override
    public User getByEmail(String email) {
        logger.info("getting user by email {}", email);
        User user = jdbcTemplate.queryForObject(GET_BY_EMAIL_SQL, USER_ROW_MAPPER, email);
        return user;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
