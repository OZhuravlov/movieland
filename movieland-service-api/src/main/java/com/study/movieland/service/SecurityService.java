package com.study.movieland.service;

import com.study.movieland.entity.User;

import java.util.UUID;

public interface SecurityService {

    UUID getLogin(User user, String password);

    void getLogout(UUID uuid);

}
