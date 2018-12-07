package com.study.movieland.web.controller.interceptor;

import com.study.movieland.entity.Role;
import com.study.movieland.web.holder.UserHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class ReviewInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) {

        return (UserHolder.getCurrentUser().getRole().equals(Role.USER)
                || UserHolder.getCurrentUser().getRole().equals(Role.ADMIN));
    }

}
