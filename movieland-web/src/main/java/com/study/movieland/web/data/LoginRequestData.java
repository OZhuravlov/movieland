package com.study.movieland.web.data;

public class LoginRequestData {
    private String email;
    private String password;

    public LoginRequestData(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
