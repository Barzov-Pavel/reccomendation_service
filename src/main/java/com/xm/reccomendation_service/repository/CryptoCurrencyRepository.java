package com.xm.reccomendation_service.repository;

import com.xm.reccomendation_service.model.CryptoCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CryptoCurrencyRepository extends JpaRepository<CryptoCurrency, Integer> {

    List<CryptoCurrency> findAllBySymbolAndTimestampBetween(String symbol, LocalDateTime startDate, LocalDateTime endDate);

}
