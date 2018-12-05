package com.study.movieland.web.controller.handler;

import com.study.movieland.exception.UserAuthenticationException;
import com.study.movieland.web.error.JsonError;
import com.study.movieland.web.exception.BadRequestParamException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private final static String NOT_FOUND_MESSAGE = "Requested Data Not Found";
    private final static String BAD_REQUEST_MESSAGE = "Bad Request params";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = NOT_FOUND_MESSAGE)
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(Exception e) {
        logger.warn("Error: {}", e);
        return JsonError.getJsonMessage(e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = BAD_REQUEST_MESSAGE)
    @ExceptionHandler(BadRequestParamException.class)
    public String handleBadRequestParamException(Exception e) {
        logger.warn("Error: {}", e);
        return JsonError.getJsonMessage(e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = BAD_REQUEST_MESSAGE)
    @ExceptionHandler(UserAuthenticationException.class)
    public String handleUserAuthenticationException(Exception e) {
        logger.warn("Error: {}", e);
        return JsonError.getJsonMessage(e.getMessage());
    }


}