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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/login")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private UserService userService;
    private SecurityService securityService;

    @RequestMapping(method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public LoginResponseData getLogin(@RequestBody LoginRequestData loginRequestData) {
        logger.info("Get login");
        User user = userService.getUserByEmail(loginRequestData.getEmail());
        UUID uuid = securityService.getLogin(user, loginRequestData.getPassword());
        LoginResponseData loginResponseData = new LoginResponseData(uuid, user.getNickname());
        logger.trace("Returning {} user", user);
        return loginResponseData;
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
