package com.study.movieland.dao.jdbc;

import com.study.movieland.dao.UserDao;
import com.study.movieland.dao.jdbc.mapper.UserRowMapper;
import com.study.movieland.dao.jdbc.util.DaoUtils;
import com.study.movieland.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JdbcUserDao implements UserDao {

    private static final String GET_BY_EMAIL_SQL = "SELECT id, nickname, email, password, sole FROM users WHERE email = ?";
    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private JdbcTemplate jdbcTemplate;

    public Optional<User> getUser(String email, String password) {
        logger.info("getting user by email {}", email);
        String warnMessage = "Not such user";
        try {
            User user = jdbcTemplate.queryForObject(GET_BY_EMAIL_SQL, USER_ROW_MAPPER, email);
            if(!user.getPassword().equals(DaoUtils.getEncryptedPassword(password, user.getSole()))){
                return Optional.empty();
            }
            return Optional.of(user);
        } catch (IncorrectResultSizeDataAccessException e) {
            logger.warn(warnMessage);
            return Optional.empty();
        }
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
