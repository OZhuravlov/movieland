package com.study.movieland.dao.cache;

import com.study.movieland.dao.CurrencyDao;
import com.study.movieland.entity.Currency;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class CacheCurrencyDaoTest {

    CacheCurrencyDao currencyDao;
    StubCurrencyDao stubCurrencyDao;

    @Before
    public void init() {
        currencyDao = new CacheCurrencyDao();
        stubCurrencyDao = new StubCurrencyDao();
        currencyDao.setCurrencyDao(stubCurrencyDao);
    }

    @Test
    public void initAndGetAllTest() {
        currencyDao.init();
        Map<Currency, Double> currencies = currencyDao.getAll();
        assertEquals(3, currencies.size());
        assertThat(currencies, is(stubCurrencyDao.getAll()));
    }

    @Test
    public void getRateTest() {
        currencyDao.init();
        Currency currencyUAH = Currency.UAH;
        double rateUAH = currencyDao.getRate(currencyUAH);
        assertEquals(0, Double.compare(1d, rateUAH));

        Currency currencyUSD = Currency.USD;
        double rateUSD = currencyDao.getRate(currencyUSD);
        assertEquals(0, Double.compare(20.20d, rateUSD));

        Currency currencyEUR = Currency.EUR;
        double rateEUR = currencyDao.getRate(currencyEUR);
        assertEquals(0, Double.compare(30.30d, rateEUR));

    }

    private static class StubCurrencyDao implements CurrencyDao {
        Map<Currency, Double> currencies = new HashMap<Currency, Double>() {{
            put(Currency.UAH, 1d);
            put(Currency.USD, 20.20d);
            put(Currency.EUR, 30.30d);
        }};

        @Override
        public Map<Currency, Double> getAll() {
            return currencies;
        }

        @Override
        public double getRate(Currency currency) {
            return currencies.get(currency);
        }

    }
}