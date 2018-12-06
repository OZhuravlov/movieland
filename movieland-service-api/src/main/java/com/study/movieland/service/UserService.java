package com.study.movieland.service;

import com.study.movieland.entity.User;

import java.util.Optional;

public interface UserService {

    Optional<User> getUser(String email, String password);

}
