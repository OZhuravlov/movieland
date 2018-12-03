package com.study.movieland.web.error;

public class JsonError {

    public static String getJsonMessage(String message) {
        return "{\"error\": " + message + "}";
    }
}
