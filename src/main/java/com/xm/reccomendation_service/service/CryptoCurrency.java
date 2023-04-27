package com.xm.reccomendation_service.service;

import com.xm.reccomendation_service.dto.CryptoCurrencyStatsDto;
import com.xm.reccomendation_service.dto.NormalizedRangeCryptoCurrencyDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Change or get data via repository, make business logic and return data to controller.
 */
public interface CryptoCurrency {

    /**
     * Get cryptocurrency with highest normalized range for a certain date.
     *
     * @param date the date for which the data should be returned
     * @return NormalizedRangeCryptoCurrencyDto which contains cryptocurrency with highest normalized range
     */
    Optional<NormalizedRangeCryptoCurrencyDto> getCryptoCurrencyWithHighestNormalizedRange(LocalDate date);

    /**
     * Get cryptocurrencies sorted by normalized range.
     *
     * @return List of NormalizedRangeCryptoCurrencyDto which contains descending sorted cryptocurrencies by normalized range
     */
    List<NormalizedRangeCryptoCurrencyDto> getCryptoCurrenciesSortedByNormalizedRange();

    /**
     * Get cryptocurrency stats with oldest/newest/min/max price.
     *
     * @param cryptoCurrencyName the symbol of cryptocurrency
     * @param year the year for which the data should be returned
     * @param month the month for which the data should be returned
     * @return CryptoCurrencyStatsDto with oldest/newest/min/max price
     */
    CryptoCurrencyStatsDto getCryptoCurrencyStats(String cryptoCurrencyName, Integer year, Integer month);

    /**
     * Save data from dto to database. Use generic type for possibility add new kinds of dto.
     *
     * @param dtoObjects the List of dto objects which should be saved to database
     * @param <T> we can add new kinds of dto objects
     */
    <T> void saveAllDtos(List<T> dtoObjects);

    /**
     * Delete all data from database.
     */
    void deleteAll();
}
