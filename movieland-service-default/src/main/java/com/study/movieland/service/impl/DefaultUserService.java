package com.study.movieland.service.impl;

import com.study.movieland.dao.UserDao;
import com.study.movieland.entity.User;
import com.study.movieland.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultUserService implements UserService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private UserDao userDao;

    @Override
    public Optional<User> getUser(String email, String password   ) {
        logger.debug("Get user by email");
        Optional<User> userOptional = userDao.getUser(email, password);
        logger.trace("Return user", userOptional);
        return userOptional;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
