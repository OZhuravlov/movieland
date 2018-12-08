package com.study.movieland.web.data;

import lombok.Data;

import java.util.List;

@Data
public class MovieEditRequestData {
    private String nameNative;
    private String nameRussian;
    private String picturePath;
    private List<Integer> countries;
    private List<Integer> genres;
}
