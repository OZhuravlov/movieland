package com.study.movieland.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.study.movieland.view.Views;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Movie {

    private static final Currency DEFAULT_CURRENCY = Currency.UAH;

    @JsonView(Views.Summary.class)
    int id;
    @JsonView(Views.Summary.class)
    private String nameNative;
    @JsonView(Views.Summary.class)
    private String nameRussian;
    @JsonView(Views.Summary.class)
    private int yearOfRelease;
    @JsonView(Views.Summary.class)
    private Double rating;
    @JsonView(Views.Summary.class)
    private Double price;
    @JsonView(Views.Summary.class)
    private Currency currency;
    @JsonView(Views.Summary.class)
    private String picturePath;

    private String description;
    private List<Country> countries;
    private List<Genre> genres;
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
