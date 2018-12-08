package com.study.movieland.web.data;

import com.study.movieland.entity.Country;
import com.study.movieland.entity.Genre;
import lombok.Data;

import java.util.List;

@Data
public class MovieAddRequestData {
    private String nameNative;
    private String nameRussian;
    private int yearOfRelease;
    private String description;
    private Double price;
    private String picturePath;
    private List<Country> countries;
    private List<Genre> genres;

}
