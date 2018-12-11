package com.study.movieland.service.impl;

import com.study.movieland.data.NbuCurrencyRate;
import com.study.movieland.entity.Currency;
import com.study.movieland.entity.Movie;
import com.study.movieland.service.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class NbuCurrencyService implements CurrencyService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Currency baseCurrency;

    private volatile Map<Currency, Double> rates;
    private RestTemplate restTemplate;
    private String url;

    @Autowired
    public NbuCurrencyService(@Value("${service.currency.base:UAH}") Currency baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    @PostConstruct
    @Scheduled(cron = "${scheduler.service.currency.cron}")
    public void init() {
        logger.info("Refreshing cache");
        Map<Currency, Double> rateMap = getAllRates();
        logger.info("Got {} rates from dao", rateMap.size());
        rates = rateMap;
    }

    @Override
    public void enrichMoviePriceInCurrency(Movie movie, Currency currency) {
        logger.info("Convert movie id {} price into {}", movie.getId(), currency.toString());
        double convertedPrice = getConvertedPrice(movie.getPrice(), currency);
        movie.setCurrency(currency);
        movie.setPriceInCurrency(convertedPrice);
    }

    private double getConvertedPrice(double price, Currency currency) {
        if (currency.equals(baseCurrency)) {
            return price;
        }
        double convertedPrice = price / rates.get(currency);
        return convertedPrice;
    }

    Map<Currency, Double> getAllRates() {
        logger.info("getting rate for all currencies");
        LocalDateTime exchangeDate = LocalDateTime.now();
        String exchangeDateParam = exchangeDate.format(FORMATTER);
        NbuCurrencyRate[] nbuCurrencyRates = restTemplate.getForObject(url, NbuCurrencyRate[].class, exchangeDateParam);
        logger.trace("got currency rates: {}", Arrays.asList(nbuCurrencyRates));

        Map<Currency, Double> rateMap = new HashMap<>();
        for (NbuCurrencyRate nbuCurrencyRate : nbuCurrencyRates) {
            String currencyCode = nbuCurrencyRate.getCurrencyCode();
            if (Currency.isExists(currencyCode)) {
                rateMap.put(Currency.getValue(currencyCode), nbuCurrencyRate.getRate());
            }
        }
        logger.debug("Currency rates from NBU {}", rateMap);
        return rateMap;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${service.currency.url}")
    public void setUrl(String url) {
        this.url = url;
    }

}
