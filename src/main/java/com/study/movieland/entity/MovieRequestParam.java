package com.study.movieland.entity;

import lombok.Data;

@Data
public class MovieRequestParam {
    private SortDirection ratingSorting;
    private SortDirection priceSorting;
}
