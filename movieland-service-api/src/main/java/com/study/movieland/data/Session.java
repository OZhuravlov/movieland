package com.study.movieland.data;

import com.study.movieland.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class Session {
    private UUID token;
    private User user;
    private LocalDateTime expireDate;

    public Session(User user) {
        this.user = user;
    }

    public UUID getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }
}
