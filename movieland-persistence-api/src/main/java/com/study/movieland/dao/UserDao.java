package com.study.movieland.dao;

import com.study.movieland.entity.User;

import java.util.Optional;

public interface UserDao {

    Optional<User> getUser(String email, String password);

}
