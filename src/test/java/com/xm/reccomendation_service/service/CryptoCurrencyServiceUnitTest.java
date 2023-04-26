package com.xm.reccomendation_service.service;

import com.xm.reccomendation_service.dto.CryptoCurrencyDto;
import com.xm.reccomendation_service.dto.CryptoCurrencyStatsDto;
import com.xm.reccomendation_service.model.CryptoCurrency;
import com.xm.reccomendation_service.repository.CryptoCurrencyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.xm.reccomendation_service.CryptoCurrencyTestUtils.MAX_PRICE_VALUE;
import static com.xm.reccomendation_service.CryptoCurrencyTestUtils.MIN_PRICE_VALUE;
import static com.xm.reccomendation_service.CryptoCurrencyTestUtils.NEWEST_PRICE_VALUE;
import static com.xm.reccomendation_service.CryptoCurrencyTestUtils.OLDEST_PRICE_VALUE;
import static com.xm.reccomendation_service.CryptoCurrencyTestUtils.getCryptoCurrencies;
import static com.xm.reccomendation_service.CryptoCurrencyTestUtils.getCryptoCurrencyDtos;
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
    void should_get_crypto_currency_statistics() {
        // Given
        String cryptoCurrencyName = "BTC";
        Integer year = 2022;
        Integer month = 1;
        List<CryptoCurrency> cryptoCurrencies = getCryptoCurrencies();
        when(repository.findAllBySymbolAndTimestampBetween(
                any(String.class),
                any(LocalDateTime.class),
                any(LocalDateTime.class)))
                .thenReturn(cryptoCurrencies);
        // When
        CryptoCurrencyStatsDto result = service.getCryptoCurrencyStats(cryptoCurrencyName, year, month);
        // Then
        assertEquals(MIN_PRICE_VALUE, result.getMinValue());
        assertEquals(MAX_PRICE_VALUE, result.getMaxValue());
        assertEquals(OLDEST_PRICE_VALUE, result.getOldestValue());
        assertEquals(NEWEST_PRICE_VALUE, result.getNewestValue());
    }

    @Test
    void should_lunch_save_all_method_from_repository() {
        // Given
        List<CryptoCurrencyDto> dtos = getCryptoCurrencyDtos();
        when(repository.saveAll(anyList())).thenReturn(Collections.emptyList());
        // When
        service.saveAll(dtos);
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
