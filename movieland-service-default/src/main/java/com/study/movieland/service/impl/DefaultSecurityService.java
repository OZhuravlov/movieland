package com.study.movieland.service.impl;

import com.study.movieland.data.Session;
import com.study.movieland.entity.User;
import com.study.movieland.exception.UserAuthenticationException;
import com.study.movieland.service.SecurityService;
import com.study.movieland.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DefaultSecurityService implements SecurityService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Map<String, Session> sessions = new ConcurrentHashMap<>();

    private UserService userService;

    @Value("${security.service.sessionMaxDurationInMinutes}")
    private Long maxSessionDurationInMinutes;

    @Scheduled(fixedDelayString = "${scheduler.service.security.fixedDelayInMilliseconds}",
            initialDelayString = "${scheduler.service.security.initDelayInMilliseconds}")
    public void cleanExpiredSessions() {
        logger.info("cleaning Expired Sessions");
        sessions.entrySet().removeIf(e -> e.getValue().getExpireDate().isBefore(LocalDateTime.now()));
    }

    @Override
    public Session doLogin(final String email, String password) {
        Optional<User> userOptional = userService.getUser(email, password);
        if (!userOptional.isPresent()) {
            throw new UserAuthenticationException("Login failed. User/Password is not valid");
        }
        User user = userOptional.get();
        user.setPassword(null);
        user.setSole(null);
        logger.info("doLogin email {}", user.getEmail());
        sessions.entrySet().removeIf(e -> e.getValue().getUser().getEmail().equals(user.getEmail()));
        Session session = new Session(user,
                LocalDateTime.now().plusMinutes(maxSessionDurationInMinutes),
                UUID.randomUUID().toString());
        sessions.put(session.getToken(), session);
        logger.debug("Return session {}", session);
        return session;
    }

    @Override
    public void doLogout(String token) {
        logger.debug("doLogout {}", token);
        sessions.remove(token);
    }

    @Override
    public Optional<User> getUserByToken(String token) {
        if (token == null || !sessions.containsKey(token)) {
            return Optional.empty();
        }
        if (sessions.get(token).getExpireDate().isBefore(LocalDateTime.now())) {
            sessions.remove(token);
            return Optional.empty();
        }
        return Optional.of(sessions.get(token).getUser());
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


}
