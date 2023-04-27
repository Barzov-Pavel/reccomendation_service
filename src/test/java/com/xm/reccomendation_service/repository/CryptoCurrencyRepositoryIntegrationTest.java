package com.xm.reccomendation_service.repository;

import com.xm.reccomendation_service.model.CryptoCurrency;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static com.xm.reccomendation_service.CryptoCurrencyTestUtils.BTC_SYMBOL;
import static com.xm.reccomendation_service.CryptoCurrencyTestUtils.ETH_SYMBOL;
import static com.xm.reccomendation_service.CryptoCurrencyTestUtils.LTC_SYMBOL;
import static com.xm.reccomendation_service.CryptoCurrencyTestUtils.getCryptoCurrenciesForDatabase;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CryptoCurrencyRepositoryIntegrationTest {

    private static final List<CryptoCurrency> cryptoCurrencies = getCryptoCurrenciesForDatabase();

    private List<CryptoCurrency> savedCurrencies;
    @Autowired
    private CryptoCurrencyRepository repository;

    @BeforeAll
    void init() {
        repository.deleteAll();
        savedCurrencies = repository.saveAll(cryptoCurrencies);
    }

    @AfterAll
    void clear() {
        repository.deleteAll(savedCurrencies);
    }

    @Test
    void should_find_all_by_symbol_and_timestamp_between() {
        // Given
        LocalDateTime startDate = LocalDateTime.of(2022, 3, 2, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2022, 3, 30, 23, 59, 59);
        // When
        List<CryptoCurrency> result = repository.findAllBySymbolAndTimestampBetween(BTC_SYMBOL, startDate, endDate);
        // Then
        assertEquals(2, result.size());
    }

    @Test
    void should_find_all_symbol() {
        // Given
        // When
        List<String> result = repository.findAllSymbols();
        // Then
        assertEquals(3, result.size());
        assertThat(result).containsExactlyInAnyOrder(BTC_SYMBOL, LTC_SYMBOL, ETH_SYMBOL);
    }

    @Test
    void should_find_all_by_symbol() {
        // Given
        // When
        List<CryptoCurrency> result = repository.findAllBySymbol(BTC_SYMBOL);
        // Then
        assertEquals(4, result.size());
    }
}
