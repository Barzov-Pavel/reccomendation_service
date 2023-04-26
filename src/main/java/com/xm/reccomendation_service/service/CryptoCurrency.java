package com.xm.reccomendation_service.service;

import com.xm.reccomendation_service.dto.CryptoCurrencyStatsDto;
import com.xm.reccomendation_service.dto.NormalizedRangeCryptoCurrencyDto;

import java.util.List;

public interface CryptoCurrency {

    List<NormalizedRangeCryptoCurrencyDto> getCryptoCurrenciesSortedByNormalizedRange();

    CryptoCurrencyStatsDto getCryptoCurrencyStats(String cryptoCurrencyName, Integer year, Integer month);

    <T> void saveAll(List<T> dtoObjects);

    void deleteAll();
}
