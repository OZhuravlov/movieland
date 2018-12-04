package com.study.movieland.web.data;

import java.util.UUID;

public class LoginResponseData {
    private UUID uuid;
    private String nickname;

    public LoginResponseData(UUID uuid, String nickname) {
        this.uuid = uuid;
        this.nickname = nickname;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getNickname() {
        return nickname;
    }
}
