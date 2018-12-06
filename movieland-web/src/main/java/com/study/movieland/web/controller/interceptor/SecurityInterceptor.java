package com.study.movieland.web.controller.interceptor;

import com.study.movieland.entity.User;
import com.study.movieland.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;

@Component
public class SecurityInterceptor extends HandlerInterceptorAdapter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SecurityService securityService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) {
        String uuidParam = request.getHeader("uuid");
        if (uuidParam != null) {
            UUID uuid = UUID.fromString(request.getHeader("uuid"));
            Optional<User> optionalUser = securityService.getUserByToken(uuid);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                logger.info("Recognise user {}", user.getNickname());
                request.getSession().setAttribute("user", user);
            }
        }
        return true;
    }
}
