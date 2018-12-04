package com.study.movieland.service.impl;

import com.study.movieland.data.Session;
import com.study.movieland.entity.User;
import com.study.movieland.exception.UserAuthenticationException;
import com.study.movieland.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DefaultSecurityService implements SecurityService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Map<UUID, Session> sessions = new ConcurrentHashMap<>();

    @Value("${security.service.sessionMaxDurationInMinutes}")
    private Long maxSessionDurationInMinutes;


    @Override
    public UUID getLogin(User user, String password) {
        logger.debug("User login");
        if (!user.getPassword().equals(password)) {
            logger.warn("Login failed. Password is not valid");
            throw new UserAuthenticationException("Password not valid");
        }
        for (Session session : sessions.values()) {
            if (user.getEmail().equals(session.getUser().getEmail())) {
                logger.debug("Found user session. Renew");
                setToken(session);
                return session.getToken();
            }
        }
        Session session = new Session(user);
        setToken(session);
        UUID token = session.getToken();
        sessions.put(token, session);
        logger.trace("Return token {}", token);
        return token;
    }

    @Override
    public void getLogout(UUID uuid) {
        sessions.remove(uuid);
    }

    private void setToken(Session session) {
        session.setToken(UUID.randomUUID());
        session.setExpireDate(LocalDateTime.now().plusMinutes(maxSessionDurationInMinutes));
    }

}
