package com.xm.reccomendation_service.service;


import com.xm.reccomendation_service.dto.CryptoCurrencyDto;
import com.xm.reccomendation_service.dto.CryptoCurrencyStatsDto;
import com.xm.reccomendation_service.dto.NormalizedRangeCryptoCurrencyDto;
import com.xm.reccomendation_service.repository.CryptoCurrencyRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.xm.reccomendation_service.util.CryptoCurrencyUtils.createNormalizedRangeCryptoCurrencyDto;
import static com.xm.reccomendation_service.util.CryptoCurrencyUtils.getEndOfDayDateTime;
import static com.xm.reccomendation_service.util.CryptoCurrencyUtils.getEndOfMonthDateTime;
import static com.xm.reccomendation_service.util.CryptoCurrencyUtils.getMaxValue;
import static com.xm.reccomendation_service.util.CryptoCurrencyUtils.getMinValue;
import static com.xm.reccomendation_service.util.CryptoCurrencyUtils.getNewestValue;
import static com.xm.reccomendation_service.util.CryptoCurrencyUtils.getOldestValue;

@Service
public class CryptoCurrencyService implements CryptoCurrency {

    private static final int FIRST_DAY_OF_MONTH = 1;
    private static final int ZERO_TIME = 0;

    private final CryptoCurrencyRepository repository;

    public CryptoCurrencyService(CryptoCurrencyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<NormalizedRangeCryptoCurrencyDto> getCryptoCurrencyWithHighestNormalizedRange(LocalDate date) {
        LocalDateTime startDateTime = date.atStartOfDay();
        LocalDateTime endDateTime = getEndOfDayDateTime(startDateTime);
        List<String> currencySymbols = repository.findAllSymbols();
        List<NormalizedRangeCryptoCurrencyDto> rangeCryptoCurrencyDtos = new ArrayList<>();
        for (String symbol : currencySymbols) {
            List<com.xm.reccomendation_service.model.CryptoCurrency> cryptoCurrencies = repository
                    .findAllBySymbolAndTimestampBetween(symbol, startDateTime, endDateTime);
            rangeCryptoCurrencyDtos.add(createNormalizedRangeCryptoCurrencyDto(symbol, cryptoCurrencies));
        }
        return rangeCryptoCurrencyDtos.stream().max(Comparator.comparing(NormalizedRangeCryptoCurrencyDto::getNormalizedRange));
    }

    @Override
    public List<NormalizedRangeCryptoCurrencyDto> getCryptoCurrenciesSortedByNormalizedRange() {
        List<String> currencySymbols = repository.findAllSymbols();
        List<NormalizedRangeCryptoCurrencyDto> rangeCryptoCurrencyDtos = new ArrayList<>();
        for (String symbol : currencySymbols) {
            List<com.xm.reccomendation_service.model.CryptoCurrency> cryptoCurrencies = repository
                    .findAllBySymbol(symbol);
            rangeCryptoCurrencyDtos.add(createNormalizedRangeCryptoCurrencyDto(symbol, cryptoCurrencies));
        }
        rangeCryptoCurrencyDtos.sort(Comparator.comparing(NormalizedRangeCryptoCurrencyDto::getNormalizedRange).reversed());
        return rangeCryptoCurrencyDtos;
    }

    @Override
    public CryptoCurrencyStatsDto getCryptoCurrencyStats(String cryptoCurrencyName, Integer year, Integer month) {
        LocalDateTime startDateTime = LocalDateTime
                .of(year, month, FIRST_DAY_OF_MONTH, ZERO_TIME, ZERO_TIME, ZERO_TIME);
        LocalDateTime endDateTime = getEndOfMonthDateTime(startDateTime);
        List<com.xm.reccomendation_service.model.CryptoCurrency> cryptoCurrencies = repository
                .findAllBySymbolAndTimestampBetween(cryptoCurrencyName, startDateTime, endDateTime);
        BigDecimal minValue = getMinValue(cryptoCurrencies);
        BigDecimal maxValue = getMaxValue(cryptoCurrencies);
        BigDecimal oldestValue = getOldestValue(cryptoCurrencies);
        BigDecimal newestValue = getNewestValue(cryptoCurrencies);
        return new CryptoCurrencyStatsDto(minValue, maxValue, oldestValue, newestValue);
    }

    @Override
    public <T> void saveAllDtos(List<T> dtoObjects) {
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
