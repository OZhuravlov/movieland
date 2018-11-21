package com.study.movieland.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class Movie {
    int id;
    private String nameNative;
    private String nameRussian;
    private String yearOfRelease;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameNative() {
        return nameNative;
    }

    public void setNameNative(String nameNative) {
        this.nameNative = nameNative;
    }

    public String getNameRussian() {
        return nameRussian;
    }

    public void setNameRussian(String nameRussian) {
        this.nameRussian = nameRussian;
    }

    public String getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(String yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public List<Country> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }

    public List<Genre> getGenreList() {
        return genreList;
    }

    public void setGenreList(List<Genre> genreList) {
        this.genreList = genreList;
    }
}
