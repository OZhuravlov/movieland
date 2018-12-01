package com.study.movieland.web.converter;

import com.study.movieland.entity.Currency;
import com.study.movieland.web.exception.BadRequestParamException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CurrencyParamConverterTest {

    @Test
    public void setAsTextTest() {
        CurrencyParamConverter converter = new CurrencyParamConverter();

        String param = "UAH";
        converter.setAsText(param);
        Currency currency = (Currency) converter.getValue();
        assertEquals(Currency.UAH, currency);

        String param2 = "usd";
        converter.setAsText(param2);
        Currency currency2 = (Currency) converter.getValue();
        assertEquals(Currency.USD, currency2);

        String param3 = "Eur";
        converter.setAsText(param3);
        Currency currency3 = (Currency) converter.getValue();
        assertEquals(Currency.EUR, currency3);

    }

    @Test(expected = BadRequestParamException.class)
    public void setAsTextExceptionTest() {
        CurrencyParamConverter converter = new CurrencyParamConverter();
        String param = "bad";
        converter.setAsText(param);
    }
}