package com.study.movieland.web.controller.interceptor;

import com.study.movieland.entity.Role;
import com.study.movieland.entity.User;
import com.study.movieland.exception.UserAuthenticationException;
import com.study.movieland.web.annotation.ProtectedBy;
import com.study.movieland.web.holder.UserHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Service
public class ReviewInterceptor extends HandlerInterceptorAdapter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) {
        User user = UserHolder.getCurrentUser();
        if (user != null) {
            Role userRole = user.getRole();
            HandlerMethod method = (HandlerMethod) handler;
            ProtectedBy protectedBy = method.getMethodAnnotation(ProtectedBy.class);
            Role[] roles = protectedBy.allowedRoles();
            if (Arrays.stream(roles).anyMatch(r -> r == userRole)) {
                return true;
            }
        }
        String errorMessage = "Not allowed to perform such operation";
        logger.warn(errorMessage);
        throw new UserAuthenticationException(errorMessage);
    }

}
