package com.study.movieland.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
public class Movie {

    int id;
    private String nameNative;
    private String nameRussian;
    private int yearOfRelease;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Country> countryList;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Genre> genreList;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;
    private Double rating;
    private Double price;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String picturePath;

    public Movie(int id, String nameNative, String nameRussian, int yearOfRelease, Double rating, Double price, String picturePath) {
        this.id = id;
        this.nameNative = nameNative;
        this.nameRussian = nameRussian;
        this.yearOfRelease = yearOfRelease;
        this.rating = rating;
        this.price = price;
        this.picturePath = picturePath;
    }

}
