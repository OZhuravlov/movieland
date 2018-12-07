package com.study.movieland.web.holder;

import com.study.movieland.entity.User;

public class UserHolder {
    private static final ThreadLocal<User> threadLocalScope = new ThreadLocal<>();

    public static User getCurrentUser() {
        return threadLocalScope.get();
    }

    public static void setCurrentUser(User user) {
        threadLocalScope.set(user);
    }

}