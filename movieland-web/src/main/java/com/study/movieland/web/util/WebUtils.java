package com.study.movieland.web.util;

import org.springframework.http.MediaType;

import javax.servlet.ServletContext;

public class WebUtils {

    public static MediaType getMediaTypeForFileName(ServletContext servletContext, String fileName) {
        String mineType = servletContext.getMimeType(fileName);
        MediaType mediaType = MediaType.parseMediaType(mineType);
        return mediaType;
    }

}
