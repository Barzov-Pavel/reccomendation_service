package com.xm.reccomendation_service.service;

import com.xm.reccomendation_service.dto.CryptoCurrencyDto;
import com.xm.reccomendation_service.dto.CryptoCurrencyStatsDto;
import com.xm.reccomendation_service.dto.NormalizedRangeCryptoCurrencyDto;
import com.xm.reccomendation_service.model.CryptoCurrency;
import com.xm.reccomendation_service.repository.CryptoCurrencyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.xm.reccomendation_service.CryptoCurrencyTestUtils.ETH_SYMBOL;
import static com.xm.reccomendation_service.CryptoCurrencyTestUtils.LTC_SYMBOL;
import static com.xm.reccomendation_service.CryptoCurrencyTestUtils.MAX_PRICE_VALUE;
import static com.xm.reccomendation_service.CryptoCurrencyTestUtils.MIN_PRICE_VALUE;
import static com.xm.reccomendation_service.CryptoCurrencyTestUtils.NEWEST_PRICE_VALUE;
import static com.xm.reccomendation_service.CryptoCurrencyTestUtils.OLDEST_PRICE_VALUE;
import static com.xm.reccomendation_service.CryptoCurrencyTestUtils.getCryptoCurrencies;
import static com.xm.reccomendation_service.CryptoCurrencyTestUtils.getCryptoCurrencyDtos;
import static com.xm.reccomendation_service.CryptoCurrencyTestUtils.getNormalizedRangeCryptoCurrencyDtos;
import static com.xm.reccomendation_service.CryptoCurrencyTestUtils.BTC_SYMBOL;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CryptoCurrencyServiceUnitTest {

    @Mock
    CryptoCurrencyRepository repository;

    @InjectMocks
    CryptoCurrencyService service;

    @Test
    void should_get_crypto_currencies_with_highest_normalized_range() {
        // Given
        LocalDate date = LocalDate.of(2022, 1, 3);
        LocalDateTime startDateTime = date.atStartOfDay();
        LocalDateTime endDateTime = startDateTime.withHour(23).withMinute(59).withSecond(59);
        List<String> symbols = Arrays.asList(BTC_SYMBOL, ETH_SYMBOL, LTC_SYMBOL);
        when(repository.findAllSymbols()).thenReturn(symbols);

        List<CryptoCurrency> btcCurrency = getCryptoCurrencies(BTC_SYMBOL, BigDecimal.valueOf(35000));
        List<CryptoCurrency> ethCurrency = getCryptoCurrencies(ETH_SYMBOL, BigDecimal.valueOf(2000));
        List<CryptoCurrency> ltcCurrency = getCryptoCurrencies(LTC_SYMBOL, BigDecimal.valueOf(100));

        when(repository.findAllBySymbolAndTimestampBetween(BTC_SYMBOL, startDateTime, endDateTime)).thenReturn(btcCurrency);
        when(repository.findAllBySymbolAndTimestampBetween(ETH_SYMBOL, startDateTime, endDateTime)).thenReturn(ethCurrency);
        when(repository.findAllBySymbolAndTimestampBetween(LTC_SYMBOL, startDateTime, endDateTime)).thenReturn(ltcCurrency);

        // When
        Optional<NormalizedRangeCryptoCurrencyDto> result = service.getCryptoCurrencyWithHighestNormalizedRange(date);
        // Then
        verify(repository, times(1)).findAllSymbols();
        verify(repository, times(3)).findAllBySymbolAndTimestampBetween(
                any(String.class), any(LocalDateTime.class), any(LocalDateTime.class));
        verifyNoMoreInteractions(repository);
        assertAll(
                () -> assertTrue(result.isPresent()),
                () -> assertEquals(LTC_SYMBOL, result.get().getSymbol())
        );
    }

    @Test
    void should_get_crypto_currencies_sorted_by_normalized_range() {
        // Given
        List<String> symbols = Arrays.asList(BTC_SYMBOL, ETH_SYMBOL, LTC_SYMBOL);
        when(repository.findAllSymbols()).thenReturn(symbols);

        List<CryptoCurrency> btcCurrency = getCryptoCurrencies(BTC_SYMBOL, BigDecimal.valueOf(35000));
        List<CryptoCurrency> ethCurrency = getCryptoCurrencies(ETH_SYMBOL, BigDecimal.valueOf(2000));
        List<CryptoCurrency> ltcCurrency = getCryptoCurrencies(LTC_SYMBOL, BigDecimal.valueOf(100));

        when(repository.findAllBySymbol(BTC_SYMBOL)).thenReturn(btcCurrency);
        when(repository.findAllBySymbol(ETH_SYMBOL)).thenReturn(ethCurrency);
        when(repository.findAllBySymbol(LTC_SYMBOL)).thenReturn(ltcCurrency);

        // When
        List<NormalizedRangeCryptoCurrencyDto> result = service.getCryptoCurrenciesSortedByNormalizedRange();
        // Then
        verify(repository, times(1)).findAllSymbols();
        verify(repository, times(3)).findAllBySymbol(any(String.class));
        verifyNoMoreInteractions(repository);
        assertAll(
                () -> assertEquals(3, result.size()),
                () -> assertEquals(getNormalizedRangeCryptoCurrencyDtos(), result)
        );

    }

    @Test
    void should_get_crypto_currency_statistics() {
        // Given
        Integer year = 2022;
        Integer month = 1;
        List<CryptoCurrency> cryptoCurrencies = getCryptoCurrencies();
        when(repository.findAllBySymbolAndTimestampBetween(
                any(String.class),
                any(LocalDateTime.class),
                any(LocalDateTime.class)))
                .thenReturn(cryptoCurrencies);
        // When
        CryptoCurrencyStatsDto result = service.getCryptoCurrencyStats(BTC_SYMBOL, year, month);
        // Then
        verify(repository, times(1)).findAllBySymbolAndTimestampBetween(
                any(String.class),
                any(LocalDateTime.class),
                any(LocalDateTime.class));
        verifyNoMoreInteractions(repository);
        assertAll(
                () -> assertEquals(MIN_PRICE_VALUE, result.getMinValue()),
                () -> assertEquals(MAX_PRICE_VALUE, result.getMaxValue()),
                () -> assertEquals(OLDEST_PRICE_VALUE, result.getOldestValue()),
                () -> assertEquals(NEWEST_PRICE_VALUE, result.getNewestValue())
        );

    }

    @Test
    void should_lunch_save_all_method_from_repository() {
        // Given
        List<CryptoCurrencyDto> dtos = getCryptoCurrencyDtos();
        when(repository.saveAll(anyList())).thenReturn(Collections.emptyList());
        // When
        service.saveAllDtos(dtos);
        // Then
        verify(repository, times(1)).saveAll(anyList());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void should_lunch_delete_all_method_from_repository() {
        // Given
        doNothing().when(repository).deleteAll();
        // When
        service.deleteAll();
        // Then
        verify(repository, times(1)).deleteAll();
        verifyNoMoreInteractions(repository);
    }
}
