package com.xm.reccomendation_service.util;

import com.xm.reccomendation_service.model.CryptoCurrency;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;


public class CryptoCurrencyUtils {

    private CryptoCurrencyUtils() {
    }

    public static BigDecimal getMinValue(List<CryptoCurrency> cryptoCurrencies) {
        return cryptoCurrencies.stream()
                .map(CryptoCurrency::getPrice)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }

    public static BigDecimal getMaxValue(List<CryptoCurrency> cryptoCurrencies) {
        return cryptoCurrencies.stream()
                .map(CryptoCurrency::getPrice)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }

    public static BigDecimal getOldestValue(List<CryptoCurrency> cryptoCurrencies) {
        CryptoCurrency oldest = cryptoCurrencies.stream()
                .min(Comparator.comparing(CryptoCurrency::getTimestamp))
                .orElse(null);
        return oldest != null ? oldest.getPrice() : null;
    }

    public static BigDecimal getNewestValue(List<CryptoCurrency> cryptoCurrencies) {
        CryptoCurrency newest = cryptoCurrencies.stream()
                .max(Comparator.comparing(CryptoCurrency::getTimestamp))
                .orElse(null);
        return newest != null ? newest.getPrice() : null;
    }
}
