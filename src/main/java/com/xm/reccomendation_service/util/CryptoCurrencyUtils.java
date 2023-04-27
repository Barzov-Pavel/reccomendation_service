package com.xm.reccomendation_service.util;

import com.xm.reccomendation_service.dto.NormalizedRangeCryptoCurrencyDto;
import com.xm.reccomendation_service.model.CryptoCurrency;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

/**
 * Utility class containing methods for manipulating and processing {@link CryptoCurrency} data.
 */
public class CryptoCurrencyUtils {

    private static final int LAST_HOUR = 23;
    private static final int LAST_MINUTE_SECOND = 59;

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

    public static NormalizedRangeCryptoCurrencyDto createNormalizedRangeCryptoCurrencyDto(String symbol, List<CryptoCurrency> cryptoCurrencies) {
        BigDecimal minValue = getMinValue(cryptoCurrencies);
        BigDecimal maxValue = getMaxValue(cryptoCurrencies);
        BigDecimal range = maxValue.subtract(minValue);
        BigDecimal normalizedRange = minValue.compareTo(BigDecimal.ZERO) == 0
                ? BigDecimal.ZERO
                : range.divide(minValue, MathContext.DECIMAL32);
        return new NormalizedRangeCryptoCurrencyDto(symbol, normalizedRange, minValue, maxValue);
    }

    public static LocalDateTime getEndOfMonthDateTime(LocalDateTime startDate) {
        return startDate.with(lastDayOfMonth())
                .withHour(LAST_HOUR)
                .withMinute(LAST_MINUTE_SECOND)
                .withSecond(LAST_MINUTE_SECOND);
    }

    public static LocalDateTime getEndOfDayDateTime(LocalDateTime startDate) {
        return startDate
                .withHour(LAST_HOUR)
                .withMinute(LAST_MINUTE_SECOND)
                .withSecond(LAST_MINUTE_SECOND);
    }
}
