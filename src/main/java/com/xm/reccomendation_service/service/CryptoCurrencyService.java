package com.xm.reccomendation_service.service;


import com.xm.reccomendation_service.dto.CryptoCurrencyDto;
import com.xm.reccomendation_service.dto.CryptoCurrencyStatsDto;
import com.xm.reccomendation_service.repository.CryptoCurrencyRepository;
import com.xm.reccomendation_service.util.CryptoCurrencyUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

@Service
public class CryptoCurrencyService implements CryptoCurrency {

    private static final int FIRST_DAY_OF_MONTH = 1;
    private static final int ZERO_TIME = 0;

    private final CryptoCurrencyRepository repository;

    public CryptoCurrencyService(CryptoCurrencyRepository repository) {
        this.repository = repository;
    }

    @Override
    public CryptoCurrencyStatsDto getCryptoCurrencyStats(String cryptoCurrencyName, Integer year, Integer month) {
        LocalDateTime startDate = LocalDateTime.of(year, month, FIRST_DAY_OF_MONTH, ZERO_TIME, ZERO_TIME, ZERO_TIME);
        LocalDateTime endDate = startDate.with(lastDayOfMonth());
        List<com.xm.reccomendation_service.model.CryptoCurrency> cryptoCurrencies = repository
                .findAllBySymbolAndTimestampBetween(cryptoCurrencyName, startDate, endDate);
        BigDecimal minValue = CryptoCurrencyUtils.getMinValue(cryptoCurrencies);
        BigDecimal maxValue = CryptoCurrencyUtils.getMaxValue(cryptoCurrencies);
        BigDecimal oldestValue = CryptoCurrencyUtils.getOldestValue(cryptoCurrencies);
        BigDecimal newestValue = CryptoCurrencyUtils.getNewestValue(cryptoCurrencies);
        return new CryptoCurrencyStatsDto(minValue, maxValue, oldestValue, newestValue);
    }

    @Override
    public <T> void saveAll(List<T> dtoObjects) {
        List<com.xm.reccomendation_service.model.CryptoCurrency> cryptoCurrencies = dtoObjects
                .stream()
                .map(dtoObject -> new com.xm.reccomendation_service.model.CryptoCurrency((CryptoCurrencyDto) dtoObject))
                .toList();
        repository.saveAll(cryptoCurrencies);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
