package com.study.movieland.dao;

import com.study.movieland.entity.User;

public interface UserDao {

    User getByEmail(String email);

}
