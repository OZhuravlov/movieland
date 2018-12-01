package com.study.movieland.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NbuCurrencyRate {
    private int r030;
    private String txt;
    private double rate;
    private String cc;
    private String exchangedate;
}
