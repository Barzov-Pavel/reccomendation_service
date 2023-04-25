package com.xm.reccomendation_service;

import com.xm.reccomendation_service.dto.CryptoCurrencyDto;

import java.math.BigDecimal;
import java.util.List;

public class CryptoCurrencyTestUtils {

    public static List<CryptoCurrencyDto> getCryptoCurrencyDtos() {
        return List.of(
                new CryptoCurrencyDto(1641495600000L,"BTC", BigDecimal.valueOf(43369.04)),
                new CryptoCurrencyDto(1641506400000L,"BTC", BigDecimal.valueOf(43120.634)),
                new CryptoCurrencyDto(1641546000000L,"BTC", BigDecimal.valueOf(42088.61))
                );
    }
}
