package com.xm.reccomendation_service.util;

import com.xm.reccomendation_service.model.CryptoCurrency;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.xm.reccomendation_service.CryptoCurrencyTestUtils.MAX_PRICE_VALUE;
import static com.xm.reccomendation_service.CryptoCurrencyTestUtils.MIN_PRICE_VALUE;
import static com.xm.reccomendation_service.CryptoCurrencyTestUtils.NEWEST_PRICE_VALUE;
import static com.xm.reccomendation_service.CryptoCurrencyTestUtils.OLDEST_PRICE_VALUE;
import static com.xm.reccomendation_service.CryptoCurrencyTestUtils.getCryptoCurrencies;
import static com.xm.reccomendation_service.util.CryptoCurrencyUtils.getMaxValue;
import static com.xm.reccomendation_service.util.CryptoCurrencyUtils.getMinValue;
import static com.xm.reccomendation_service.util.CryptoCurrencyUtils.getNewestValue;
import static com.xm.reccomendation_service.util.CryptoCurrencyUtils.getOldestValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CryptoCurrencyUtilsUnitTest {

    @Test
    void should_find_min_value() {
        // Given
        List<CryptoCurrency> cryptoCurrencies = getCryptoCurrencies();
        // When
        // Then
        assertEquals(MIN_PRICE_VALUE, getMinValue(cryptoCurrencies));
    }

    @Test
    void should_find_max_value() {
        // Given
        List<CryptoCurrency> cryptoCurrencies = getCryptoCurrencies();
        // When
        // Then
        assertEquals(MAX_PRICE_VALUE, getMaxValue(cryptoCurrencies));
    }

    @Test
    void should_find_oldest_value() {
        // Given
        List<CryptoCurrency> cryptoCurrencies = getCryptoCurrencies();
        // When
        // Then
        assertEquals(OLDEST_PRICE_VALUE, getOldestValue(cryptoCurrencies));
    }

    @Test
    void should_find_newest_value() {
        // Given
        List<CryptoCurrency> cryptoCurrencies = getCryptoCurrencies();
        // When
        // Then
        assertEquals(NEWEST_PRICE_VALUE, getNewestValue(cryptoCurrencies));
    }
}
