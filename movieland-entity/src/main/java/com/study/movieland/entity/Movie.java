package com.study.movieland.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Movie {

    int id;
    private String nameNative;
    private String nameRussian;
    private int yearOfRelease;
    private List<Country> countryList;
    private List<Genre> genreList;
    private String description;
    private Double rating;
    private Double price;
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
