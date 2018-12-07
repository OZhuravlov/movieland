package com.study.movieland.web.controller;

import com.study.movieland.data.Session;
import com.study.movieland.service.SecurityService;
import com.study.movieland.web.data.LoginRequestData;
import com.study.movieland.web.data.LoginResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private SecurityService securityService;

    @RequestMapping(value = "/login",
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public LoginResponseData doLogin(@RequestBody LoginRequestData loginRequestData) {
        Session session = securityService.doLogin(loginRequestData.getEmail(), loginRequestData.getPassword());
        LoginResponseData loginResponseData = new LoginResponseData(session.getToken(), session.getUser().getNickname());
        logger.debug("Returning {}", session);
        return loginResponseData;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void doLogout(@RequestHeader(value = "uuid") String token) {
        securityService.doLogout(token);
    }

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}
