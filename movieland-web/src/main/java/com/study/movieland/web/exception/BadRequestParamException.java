package com.study.movieland.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Bad Request param")
public class BadRequestParamException extends RuntimeException{

    public BadRequestParamException(String message) {
        super(message);
    }

}
