package com.study.movieland.entity;

import lombok.Data;

@Data
public class User {
    private int id;
    private String username;
    private String password;
    private String sole;
    private String nickname;
    private String email;
    private Role role;
}
