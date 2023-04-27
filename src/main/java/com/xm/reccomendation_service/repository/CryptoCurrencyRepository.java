package com.xm.reccomendation_service.repository;

import com.xm.reccomendation_service.model.CryptoCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * This repository provides methods to perform CRUD operations on {@link CryptoCurrency} entity.
 */
@Repository
public interface CryptoCurrencyRepository extends JpaRepository<CryptoCurrency, Integer> {

    List<CryptoCurrency> findAllBySymbolAndTimestampBetween(String symbol, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT DISTINCT e.symbol FROM CryptoCurrency e")
    List<String> findAllSymbols();

    List<CryptoCurrency> findAllBySymbol(String symbol);

}
