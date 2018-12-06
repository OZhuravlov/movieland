package com.study.movieland.web.data;

public class LoginResponseData {
    private String uuid;
    private String nickname;

    public LoginResponseData(String uuid, String nickname) {
        this.uuid = uuid;
        this.nickname = nickname;
    }

    public String getUuid() {
        return uuid;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public String toString() {
        return "LoginResponseData{" +
                "uuid=" + uuid +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
