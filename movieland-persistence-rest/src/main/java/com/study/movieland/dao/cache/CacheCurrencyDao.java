package com.study.movieland.dao.cache;

import com.study.movieland.dao.CurrencyDao;
import com.study.movieland.entity.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Repository
@Primary
public class CacheCurrencyDao implements CurrencyDao {

    private Currency baseCurrency;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private CurrencyDao currencyDao;
    private volatile Map<Currency, Double> rates;

    @PostConstruct
    @Scheduled(fixedDelayString = "${scheduler.dao.cache.currency.fixedDelayInMilliseconds}",
            initialDelayString = "${scheduler.dao.cache.currency.initDelayInMilliseconds}")
    public void init() {
        logger.info("Refreshing cache");
        Map<Currency, Double> rateMap = currencyDao.getAll();
        logger.info("Got {} rates from dao", rateMap.size());
        rates = rateMap;
    }

    @Override
    public Map<Currency, Double> getAll() {
        logger.info("get All rates from cache");
        return new HashMap<>(rates);
    }

    @Override
    public double getRate(Currency currency) {
        logger.info("getting rate by currency {} from cache", currency);
        return rates.get(currency);
    }

    @Autowired
    public void setCurrencyDao(CurrencyDao currencyDao) {
        this.currencyDao = currencyDao;
    }


    @Value("${dao.currency.base:UAH}")
    public void setBaseCurrency(Currency baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

}
