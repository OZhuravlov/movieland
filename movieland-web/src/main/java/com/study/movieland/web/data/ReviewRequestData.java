package com.study.movieland.web.data;

import lombok.Data;

@Data
public class ReviewRequestData {
    private int movieId;
    private String text;
}
