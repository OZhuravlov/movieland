package com.study.movieland.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NbuCurrencyRate {
    @JsonProperty("r030")
    private int currencyId;

    @JsonProperty("txt")
    private String currencyName;

    @JsonProperty("rate")
    private double rate;

    @JsonProperty("cc")
    private String currencyCode;

    @JsonProperty("exchangedate")
    private String exchangeDate;
}
