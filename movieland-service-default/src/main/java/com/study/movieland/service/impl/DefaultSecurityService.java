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
import java.util.Optional;
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
    public UUID doLogin(final User user, String password) {
        Optional.ofNullable(user)
                .filter(u -> u.getPassword().equals(password))
                .orElseThrow(() -> new UserAuthenticationException("Login failed. User/Password is not valid"));
        user.setPassword(null);
        logger.debug("User {} doLogin", user.getEmail());
        sessions.entrySet().stream()
                .filter(e -> e.getValue().getUser().getEmail().equals(user.getEmail()))
                .forEach(t -> sessions.remove(t.getKey()));
        Session session = new Session(user);
        session.setExpireDate(LocalDateTime.now().plusMinutes(maxSessionDurationInMinutes));
        UUID token = UUID.randomUUID();
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
    public Optional<User> getUserByToken(UUID uuid) {
        if (!sessions.containsKey(uuid)) {
            return Optional.empty();
        }
        if (sessions.get(uuid).getExpireDate().isBefore(LocalDateTime.now())) {
            sessions.remove(uuid);
            return Optional.empty();
        }
        return Optional.of(sessions.get(uuid).getUser());
    }

}
