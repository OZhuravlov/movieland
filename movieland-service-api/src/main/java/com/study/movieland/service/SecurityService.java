package com.study.movieland.service;

import com.study.movieland.data.Session;

import java.util.Optional;

public interface SecurityService {

    Session doLogin(String email, String password);

    void doLogout(String token);

    Optional<Session> getSession(String token);

}
