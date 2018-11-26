package com.study.movieland.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderBy {
    private String fieldName;
    private SortDirection sortDirection;
}
