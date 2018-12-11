package com.study.movieland.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.study.movieland.view.Views;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Movie {

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
    private String picturePath;
    @JsonView(Views.Summary.class)
    private Double price;

    private Currency currency;
    private String description;
    private volatile List<Country> countries;
    private volatile List<Genre> genres;
    private volatile List<Review> reviews;

    public Movie(int id, String nameNative, String nameRussian, int yearOfRelease, Double rating, Double price, String picturePath) {
        this.id = id;
        this.nameNative = nameNative;
        this.nameRussian = nameRussian;
        this.yearOfRelease = yearOfRelease;
        this.rating = rating;
        this.price = price;
        this.picturePath = picturePath;
    }

    public Movie(Movie movieToCopy) {
        this.id = movieToCopy.id;
        this.nameNative = movieToCopy.nameNative;
        this.nameRussian = movieToCopy.nameRussian;
        this.yearOfRelease = movieToCopy.yearOfRelease;
        this.rating = movieToCopy.rating;
        this.price = movieToCopy.price;
        this.picturePath = movieToCopy.picturePath;
        this.currency = movieToCopy.currency;
        this.description = movieToCopy.description;
        this.countries = new ArrayList<>(movieToCopy.countries);
        this.genres = new ArrayList<>(movieToCopy.genres);
        this.reviews = new ArrayList<>(movieToCopy.reviews);
    }

}
