package com.study.movieland.entity;

import lombok.Data;

@Data
public class MovieRequestParam {
    private String sortFieldName;
    private SortDirection sortDirection;
}
