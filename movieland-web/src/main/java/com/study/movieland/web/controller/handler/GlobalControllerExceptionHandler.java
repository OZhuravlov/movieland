package com.study.movieland.web.controller.handler;

import com.study.movieland.exception.IncompleteOperationException;
import com.study.movieland.exception.UserAuthenticationException;
import com.study.movieland.web.error.JsonError;
import com.study.movieland.web.exception.BadRequestParamException;
import com.study.movieland.web.exception.OperationNotAllowedException;
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
    private final static String UNAUTHORIZED_MESSAGE = "User unauthorized";
    private final static String METHOD_NOT_ALLOWED_MESSAGE = "No permissions to perform such operation";
    private final static String INTERNAL_SERVER_ERROR_MESSAGE = "Cannot complete operation";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = NOT_FOUND_MESSAGE)
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(Exception e) {
        logger.warn("Error", e);
        return JsonError.getJsonMessage(e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = BAD_REQUEST_MESSAGE)
    @ExceptionHandler(BadRequestParamException.class)
    public String handleBadRequestParamException(Exception e) {
        logger.warn("Error", e);
        return JsonError.getJsonMessage(e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = UNAUTHORIZED_MESSAGE)
    @ExceptionHandler(UserAuthenticationException.class)
    public String handleUserAuthenticationException(Exception e) {
        logger.warn("Error", e);
        return JsonError.getJsonMessage(e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED, reason = METHOD_NOT_ALLOWED_MESSAGE)
    @ExceptionHandler(OperationNotAllowedException.class)
    public void handleOperationNotAllowedException(Exception e) {
        logger.warn("Error", e);
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = INTERNAL_SERVER_ERROR_MESSAGE)
    @ExceptionHandler(IncompleteOperationException.class)
    public void handleIncompleteOperationException(Exception e) {
        logger.warn("Error", e);
    }

}