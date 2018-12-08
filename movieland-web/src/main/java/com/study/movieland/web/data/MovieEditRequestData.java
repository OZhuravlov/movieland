package com.study.movieland.web.data;

import com.study.movieland.entity.Country;
import com.study.movieland.entity.Genre;
import lombok.Data;

import java.util.List;

@Data
public class MovieEditRequestData {
    private String nameNative;
    private String nameRussian;
    private String picturePath;
    private List<Country> countries;
    private List<Genre> genres;
}
