package com.study.movieland.web.controller;

import com.study.movieland.entity.User;
import com.study.movieland.service.SecurityService;
import com.study.movieland.service.UserService;
import com.study.movieland.web.data.LoginRequestData;
import com.study.movieland.web.data.LoginResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController {

    private static final String LOGOUT_OK_MESSAGE = "{\"logout\" : \"OK\"}";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private UserService userService;
    private SecurityService securityService;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public LoginResponseData doLogin(@RequestBody LoginRequestData loginRequestData) {
        logger.info("Do login");
        User user = userService.getUserByEmail(loginRequestData.getEmail());
        UUID uuid = securityService.doLogin(user, loginRequestData.getPassword());
        LoginResponseData loginResponseData = new LoginResponseData(uuid, user.getNickname());
        logger.trace("Returning {} user", user);
        return loginResponseData;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public String doLogout(@RequestHeader(value = "uuid") UUID uuid) {
        logger.info("Do logout");
        securityService.doLogout(uuid);
        return LOGOUT_OK_MESSAGE;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}
