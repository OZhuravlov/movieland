package com.study.movieland.service;

import com.study.movieland.entity.Currency;
import com.study.movieland.entity.Movie;

public interface CurrencyService {

    void enrichMoviePriceInCurrency(Movie movie, Currency currency);

}
