package com.xm.reccomendation_service.service;

import com.xm.reccomendation_service.dto.CryptoCurrencyStatsDto;
import com.xm.reccomendation_service.dto.NormalizedRangeCryptoCurrencyDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CryptoCurrency {

    Optional<NormalizedRangeCryptoCurrencyDto> getCryptoCurrencyWithHighestNormalizedRange(LocalDate date);

    List<NormalizedRangeCryptoCurrencyDto> getCryptoCurrenciesSortedByNormalizedRange();

    CryptoCurrencyStatsDto getCryptoCurrencyStats(String cryptoCurrencyName, Integer year, Integer month);

    <T> void saveAll(List<T> dtoObjects);

    void deleteAll();
}
