package com.study.movieland.service;

import com.study.movieland.entity.User;

import java.util.UUID;

public interface SecurityService {

    UUID doLogin(User user, String password);

    void doLogout(UUID uuid);

    User getUserByToken(UUID uuid);

}
