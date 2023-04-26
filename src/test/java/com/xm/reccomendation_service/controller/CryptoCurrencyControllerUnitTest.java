package com.xm.reccomendation_service.controller;

import com.xm.reccomendation_service.dto.CryptoCurrencyStatsDto;
import com.xm.reccomendation_service.dto.NormalizedRangeCryptoCurrencyDto;
import com.xm.reccomendation_service.service.CryptoCurrency;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.xm.reccomendation_service.CryptoCurrencyTestUtils.BTC_SYMBOL;
import static com.xm.reccomendation_service.CryptoCurrencyTestUtils.getNormalizedRangeCryptoCurrencyDtos;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CryptoCurrencyControllerUnitTest {

    @Mock
    CryptoCurrency cryptoCurrencyService;

    @InjectMocks
    CryptoCurrencyController controller;

    @Test
    void should_get_crypto_currency_statistics() {
        // Given
        Integer year = 2022;
        Integer month = 1;
        CryptoCurrencyStatsDto dto = new CryptoCurrencyStatsDto(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        when(cryptoCurrencyService.getCryptoCurrencyStats(BTC_SYMBOL, year, month)).thenReturn(dto);
        // When
        ResponseEntity<CryptoCurrencyStatsDto> response = controller
                .getCryptoCurrencyStats(BTC_SYMBOL, year, month);
        // Then
        verify(cryptoCurrencyService, times(1)).getCryptoCurrencyStats(BTC_SYMBOL, year, month);
        verifyNoMoreInteractions(cryptoCurrencyService);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void should_get_crypto_currencies_sorted_by_normalize_range() {
        // Given
        List<NormalizedRangeCryptoCurrencyDto> currencyDtos = getNormalizedRangeCryptoCurrencyDtos();
        when(cryptoCurrencyService.getCryptoCurrenciesSortedByNormalizedRange()).thenReturn(currencyDtos);
        // Then
        ResponseEntity<List<NormalizedRangeCryptoCurrencyDto>> response = controller.getCryptoCurrenciesSortedByNormalizedRange();
        // When
        verify(cryptoCurrencyService, times(1)).getCryptoCurrenciesSortedByNormalizedRange();
        verifyNoMoreInteractions(cryptoCurrencyService);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(currencyDtos, response.getBody());
    }

    @Test
    void should_get_crypto_currency_with_highest_normalized_range() {
        // Given
        LocalDate date = LocalDate.of(2022, 1, 3);
        NormalizedRangeCryptoCurrencyDto currencyDto = new NormalizedRangeCryptoCurrencyDto(BTC_SYMBOL, BigDecimal.valueOf(0.01428571), BigDecimal.valueOf(35000), BigDecimal.valueOf(35500));
        when(cryptoCurrencyService.getCryptoCurrencyWithHighestNormalizedRange(date)).thenReturn(Optional.of(currencyDto));
        // When
        ResponseEntity<NormalizedRangeCryptoCurrencyDto> response = controller.getCryptoCurrencyWithHighestNormalizedRange(date);
        // Then
        verify(cryptoCurrencyService, times(1)).getCryptoCurrencyWithHighestNormalizedRange(date);
        verifyNoMoreInteractions(cryptoCurrencyService);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(currencyDto, response.getBody());
    }

    @Test
    void should_return_status_no_content_when_data_absent_in_db() {
        // Given
        LocalDate date = LocalDate.of(2022, 1, 3);
        when(cryptoCurrencyService.getCryptoCurrencyWithHighestNormalizedRange(date)).thenReturn(Optional.empty());
        // When
        ResponseEntity<NormalizedRangeCryptoCurrencyDto> response = controller.getCryptoCurrencyWithHighestNormalizedRange(date);
        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
