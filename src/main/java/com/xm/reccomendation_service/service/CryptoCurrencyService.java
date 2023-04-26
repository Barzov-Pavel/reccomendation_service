package com.xm.reccomendation_service.service;


import com.xm.reccomendation_service.dto.CryptoCurrencyDto;
import com.xm.reccomendation_service.dto.CryptoCurrencyStatsDto;
import com.xm.reccomendation_service.dto.NormalizedRangeCryptoCurrencyDto;
import com.xm.reccomendation_service.repository.CryptoCurrencyRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.xm.reccomendation_service.util.CryptoCurrencyUtils.getMaxValue;
import static com.xm.reccomendation_service.util.CryptoCurrencyUtils.getMinValue;
import static com.xm.reccomendation_service.util.CryptoCurrencyUtils.getNewestValue;
import static com.xm.reccomendation_service.util.CryptoCurrencyUtils.getOldestValue;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

@Service
public class CryptoCurrencyService implements CryptoCurrency {

    private static final int FIRST_DAY_OF_MONTH = 1;
    private static final int ZERO_TIME = 0;
    private static final int LAST_HOUR = 23;
    private static final int LAST_MINUTE_SECOND = 59;

    private final CryptoCurrencyRepository repository;

    public CryptoCurrencyService(CryptoCurrencyRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<NormalizedRangeCryptoCurrencyDto> getCryptoCurrenciesSortedByNormalizedRange() {
        List<String> currencySymbols = repository.findAllSymbols();
        List<NormalizedRangeCryptoCurrencyDto> rangeCryptoCurrencyDtos = new ArrayList<>();
        for (String symbol : currencySymbols) {
            List<com.xm.reccomendation_service.model.CryptoCurrency> cryptoCurrencies = repository
                    .findAllBySymbol(symbol);
            BigDecimal minValue = getMinValue(cryptoCurrencies);
            BigDecimal maxValue = getMaxValue(cryptoCurrencies);
            BigDecimal range = maxValue.subtract(minValue);
            BigDecimal normalizedRange = minValue.compareTo(BigDecimal.ZERO) == 0
                    ? BigDecimal.ZERO
                    : range.divide(minValue, MathContext.DECIMAL32);
            rangeCryptoCurrencyDtos.add(new NormalizedRangeCryptoCurrencyDto(symbol, normalizedRange, minValue, maxValue));
        }
        rangeCryptoCurrencyDtos.sort(Comparator.comparing(NormalizedRangeCryptoCurrencyDto::getNormalizedRange).reversed());
        return rangeCryptoCurrencyDtos;
    }

    @Override
    public CryptoCurrencyStatsDto getCryptoCurrencyStats(String cryptoCurrencyName, Integer year, Integer month) {
        LocalDateTime startDate = LocalDateTime
                .of(year, month, FIRST_DAY_OF_MONTH, ZERO_TIME, ZERO_TIME, ZERO_TIME);
        LocalDateTime endDate = startDate.with(lastDayOfMonth()).withHour(LAST_HOUR)
                .withMinute(LAST_MINUTE_SECOND)
                .withSecond(LAST_MINUTE_SECOND);
        List<com.xm.reccomendation_service.model.CryptoCurrency> cryptoCurrencies = repository
                .findAllBySymbolAndTimestampBetween(cryptoCurrencyName, startDate, endDate);
        BigDecimal minValue = getMinValue(cryptoCurrencies);
        BigDecimal maxValue = getMaxValue(cryptoCurrencies);
        BigDecimal oldestValue = getOldestValue(cryptoCurrencies);
        BigDecimal newestValue = getNewestValue(cryptoCurrencies);
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
