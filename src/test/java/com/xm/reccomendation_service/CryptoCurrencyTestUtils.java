package com.xm.reccomendation_service;

import com.xm.reccomendation_service.dto.CryptoCurrencyDto;
import com.xm.reccomendation_service.dto.NormalizedRangeCryptoCurrencyDto;
import com.xm.reccomendation_service.model.CryptoCurrency;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class CryptoCurrencyTestUtils {
    public static final String BTC_SYMBOL = "BTC";
    public static final String ETH_SYMBOL = "ETH";
    public static final String LTC_SYMBOL = "LTC";

    public static final BigDecimal MIN_PRICE_VALUE = BigDecimal.valueOf(2.5);
    public static final BigDecimal MAX_PRICE_VALUE = BigDecimal.valueOf(10.5);
    public static final BigDecimal OLDEST_PRICE_VALUE = BigDecimal.valueOf(5.5);
    public static final BigDecimal NEWEST_PRICE_VALUE = BigDecimal.valueOf(3.5);

    public static List<CryptoCurrencyDto> getCryptoCurrencyDtos() {
        return List.of(
                new CryptoCurrencyDto(1641495600000L, BTC_SYMBOL, BigDecimal.valueOf(43369.04)),
                new CryptoCurrencyDto(1641506400000L, BTC_SYMBOL, BigDecimal.valueOf(43120.634)),
                new CryptoCurrencyDto(1641546000000L, BTC_SYMBOL, BigDecimal.valueOf(42088.61))
        );
    }

    public static List<NormalizedRangeCryptoCurrencyDto> getNormalizedRangeCryptoCurrencyDtos() {
        return List.of(
                new NormalizedRangeCryptoCurrencyDto(LTC_SYMBOL, BigDecimal.valueOf(5), BigDecimal.valueOf(100), BigDecimal.valueOf(600)),
                new NormalizedRangeCryptoCurrencyDto(ETH_SYMBOL, BigDecimal.valueOf(0.25), BigDecimal.valueOf(2000), BigDecimal.valueOf(2500)),
                new NormalizedRangeCryptoCurrencyDto(BTC_SYMBOL, BigDecimal.valueOf(0.01428571), BigDecimal.valueOf(35000), BigDecimal.valueOf(35500))
        );
    }

    public static List<CryptoCurrency> getCryptoCurrencies() {
        return List.of(
                new CryptoCurrency(1, LocalDateTime.of(2022, 1, 1, 0, 0, 0), BTC_SYMBOL, OLDEST_PRICE_VALUE),
                new CryptoCurrency(1, LocalDateTime.of(2022, 1, 2, 0, 0, 0), BTC_SYMBOL, MAX_PRICE_VALUE),
                new CryptoCurrency(1, LocalDateTime.of(2022, 1, 30, 23, 59, 59), BTC_SYMBOL, MIN_PRICE_VALUE),
                new CryptoCurrency(1, LocalDateTime.of(2022, 1, 31, 23, 59, 59), BTC_SYMBOL, NEWEST_PRICE_VALUE)
        );
    }

    public static List<CryptoCurrency> getCryptoCurrencies(String symbol, BigDecimal price) {
        return List.of(
                new CryptoCurrency(1, LocalDateTime.of(2022, 1, 1, 0, 0, 0), symbol, price),
                new CryptoCurrency(1, LocalDateTime.of(2022, 1, 2, 0, 0, 0), symbol, price.add(BigDecimal.valueOf(100))),
                new CryptoCurrency(1, LocalDateTime.of(2022, 1, 30, 23, 59, 59), symbol, price.add(BigDecimal.valueOf(500))),
                new CryptoCurrency(1, LocalDateTime.of(2022, 1, 31, 23, 59, 59), symbol, price.add(BigDecimal.valueOf(300)))
                );
    }
}
