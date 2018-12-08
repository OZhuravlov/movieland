package com.study.movieland.exception;

public class IncompleteOperationException extends RuntimeException {

    public IncompleteOperationException(String message) {
        super(message);
    }

    public IncompleteOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
