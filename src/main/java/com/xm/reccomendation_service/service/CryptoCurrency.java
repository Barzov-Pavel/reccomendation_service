package com.xm.reccomendation_service.service;

import com.xm.reccomendation_service.dto.CryptoCurrencyStatsDto;

import java.util.List;

public interface CryptoCurrency {

    CryptoCurrencyStatsDto getCryptoCurrencyStats(String cryptoCurrencyName, Integer year, Integer month);

    <T> void saveAll(List<T> dtoObjects);

    void deleteAll();
}
