package com.study.movieland.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Movie {

    private static final Currency DEFAULT_CURRENCY = Currency.UAH;

    int id;
    private String nameNative;
    private String nameRussian;
    private int yearOfRelease;
    private List<Country> countries;
    private List<Genre> genres;
    private String description;
    private Double rating;
    private Double price;
    private Currency currency;
    private String picturePath;
    private List<Review> reviews;

    public Movie(int id, String nameNative, String nameRussian, int yearOfRelease, Double rating, Double price, String picturePath) {
        this.id = id;
        this.nameNative = nameNative;
        this.nameRussian = nameRussian;
        this.yearOfRelease = yearOfRelease;
        this.rating = rating;
        this.price = price;
        this.currency = DEFAULT_CURRENCY;
        this.picturePath = picturePath;
    }

}
