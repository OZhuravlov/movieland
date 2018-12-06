package com.study.movieland.service;

import com.study.movieland.data.Session;
import com.study.movieland.entity.User;

import java.util.Optional;

public interface SecurityService {

    Session doLogin(String email, String password);

    void doLogout(String token);

    Optional<User> getUserByToken(String token);

}
