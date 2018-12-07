package com.study.movieland.web.data;

public class LoginRequestData {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequestData{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
