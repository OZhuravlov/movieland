package com.study.movieland.data;

import com.study.movieland.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class Session {
    private User user;
    private LocalDateTime expireDate;
    private String token;

    public Session(User user, LocalDateTime expireDate, String token) {
        this.user = user;
        this.expireDate = expireDate;
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "Session{" +
                "user=" + user +
                ", expireDate=" + expireDate +
                ", token='" + token + '\'' +
                '}';
    }
}
