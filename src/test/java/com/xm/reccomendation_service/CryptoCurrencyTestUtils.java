package com.xm.reccomendation_service;

import com.xm.reccomendation_service.dto.CryptoCurrencyDto;
import com.xm.reccomendation_service.model.CryptoCurrency;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class CryptoCurrencyTestUtils {

    public static final BigDecimal MIN_PRICE_VALUE = BigDecimal.valueOf(2.5);
    public static final BigDecimal MAX_PRICE_VALUE = BigDecimal.valueOf(10.5);
    public static final BigDecimal OLDEST_PRICE_VALUE = BigDecimal.valueOf(5.5);
    public static final BigDecimal NEWEST_PRICE_VALUE = BigDecimal.valueOf(3.5);




    public static List<CryptoCurrencyDto> getCryptoCurrencyDtos() {
        return List.of(
                new CryptoCurrencyDto(1641495600000L, "BTC", BigDecimal.valueOf(43369.04)),
                new CryptoCurrencyDto(1641506400000L, "BTC", BigDecimal.valueOf(43120.634)),
                new CryptoCurrencyDto(1641546000000L, "BTC", BigDecimal.valueOf(42088.61))
        );
    }

    public static List<CryptoCurrency> getCryptoCurrencies() {
        return List.of(
                new CryptoCurrency(1, LocalDateTime.of(2022, 1, 1, 0, 0, 0), "BTC", OLDEST_PRICE_VALUE),
                new CryptoCurrency(1, LocalDateTime.of(2022, 1, 2, 0, 0, 0), "BTC", MAX_PRICE_VALUE),
                new CryptoCurrency(1, LocalDateTime.of(2022, 1, 30, 23, 59, 59), "BTC", MIN_PRICE_VALUE),
                new CryptoCurrency(1, LocalDateTime.of(2022, 1, 31, 23, 59, 59), "BTC", NEWEST_PRICE_VALUE)
        );
    }
}
