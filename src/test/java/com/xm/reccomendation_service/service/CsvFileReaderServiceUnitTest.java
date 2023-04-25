package com.xm.reccomendation_service.service;

import com.xm.reccomendation_service.dto.CryptoCurrencyDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class CsvFileReaderServiceUnitTest {

    private static CsvFileReaderService csvFileReaderService;

    @BeforeAll
    static void init() {
        csvFileReaderService = new CsvFileReaderService();
    }

    @Test
    void should_return_list_of_dtos_when_file_exist() {
        // Given
        String path = "src/test/resources/test-data.csv";
        CryptoCurrencyDto expected = new CryptoCurrencyDto(1641506400000L, "BTC", BigDecimal.valueOf(43120.63));
        // When
        List<CryptoCurrencyDto> result = csvFileReaderService.read(path);
        // Then
        assertAll(
                () -> assertEquals(10, result.size()),
                () -> assertTrue(result.contains(expected))
        );
    }

    @Test
    void should_return_empty_list_of_dtos_when_file_not_exist() {
        // Given
        String path = "src/test/resources/absent-test-data.csv";
        // When
        List<CryptoCurrencyDto> result = csvFileReaderService.read(path);
        // Then
        assertEquals(0, result.size());
    }
}
