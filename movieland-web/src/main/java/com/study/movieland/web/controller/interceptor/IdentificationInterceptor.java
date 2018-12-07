package com.study.movieland.web.controller.interceptor;

import com.study.movieland.data.Session;
import com.study.movieland.entity.User;
import com.study.movieland.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;

@Component
public class IdentificationInterceptor extends HandlerInterceptorAdapter {

    private static final String REQUEST_ID_PARAM_NAME = "requestId";
    private static final String USERNAME_PARAM_NAME = "username";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SecurityService securityService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) {
        String token = request.getHeader("uuid");
        MDC.put(REQUEST_ID_PARAM_NAME, UUID.randomUUID().toString());
        Optional<Session> optionalSession = securityService.getSession(token);
        String username = (optionalSession.isPresent()) ? optionalSession.get().getUser().getEmail() : "guest";
        MDC.put(USERNAME_PARAM_NAME, username);
        logger.debug("Recognize request");

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        MDC.remove(REQUEST_ID_PARAM_NAME);
        MDC.remove(USERNAME_PARAM_NAME);
    }
}
