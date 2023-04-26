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
import java.util.List;

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
        String cryptoCurrencyName = "BTC";
        Integer year = 2022;
        Integer month = 1;
        CryptoCurrencyStatsDto dto = new CryptoCurrencyStatsDto(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        when(cryptoCurrencyService.getCryptoCurrencyStats(cryptoCurrencyName, year, month)).thenReturn(dto);
        // When
        ResponseEntity<CryptoCurrencyStatsDto> response = controller
                .getCryptoCurrencyStats(cryptoCurrencyName, year, month);
        // Then
        verify(cryptoCurrencyService, times(1)).getCryptoCurrencyStats(cryptoCurrencyName, year, month);
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
}
