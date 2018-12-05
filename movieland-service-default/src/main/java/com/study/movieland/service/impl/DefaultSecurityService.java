package com.study.movieland.service.impl;

import com.study.movieland.data.Session;
import com.study.movieland.entity.User;
import com.study.movieland.exception.UserAuthenticationException;
import com.study.movieland.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DefaultSecurityService implements SecurityService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Map<UUID, Session> sessions = new ConcurrentHashMap<>();

    @Value("${security.service.sessionMaxDurationInMinutes}")
    private Long maxSessionDurationInMinutes;

    @Scheduled(fixedDelayString = "${scheduler.service.security.fixedDelayInMilliseconds}",
            initialDelayString = "${scheduler.service.security.initDelayInMilliseconds}")
    public void cleanExpiredSessions() {
        logger.info("cleaning Expired Sessions");
        Iterator<Map.Entry<UUID, Session>> iterator = sessions.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<UUID, Session> entry = iterator.next();
            if (entry.getValue().getExpireDate().isBefore(LocalDateTime.now())) {
                logger.debug("Session of User {} expired", entry.getValue().getUser().getEmail());
                iterator.remove();
            }
        }
    }

    @Override
    public UUID doLogin(User user, String password) {
        logger.debug("User {} doLogin", user.getEmail());
        if (user == null || !user.getPassword().equals(password)) {
            String errorMessage = "Login failed. User/Password is not valid";
            logger.warn(errorMessage);
            throw new UserAuthenticationException(errorMessage);
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
    public void doLogout(UUID uuid) {
        logger.debug("User doLogout");
        sessions.remove(uuid);
    }

    @Override
    public User getUserByToken(UUID uuid) {
        if (!sessions.containsKey(uuid)) {
            return null;
        }
        if (sessions.get(uuid).getExpireDate().isBefore(LocalDateTime.now())) {
            sessions.remove(uuid);
            return null;
        }
        return sessions.get(uuid).getUser();
    }

    private void setToken(Session session) {
        logger.debug("User {} set token", session.getUser().getEmail());
        session.setToken(UUID.randomUUID());
        session.setExpireDate(LocalDateTime.now().plusMinutes(maxSessionDurationInMinutes));
    }

}
