package com.study.movieland.data;

import com.study.movieland.entity.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class MovieRequestParam {
    private String sortFieldName;
    private SortDirection sortDirection;
    private Currency currency;
}
