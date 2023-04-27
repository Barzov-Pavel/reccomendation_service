package com.xm.reccomendation_service.service;

import com.xm.reccomendation_service.dto.CryptoCurrencyDto;

import java.util.List;

/**
 * Reads data from the specified data path and returns a list of {@link CryptoCurrencyDto} objects.
 */
public interface Readable {
    /**
     * @param dataPath the path to the data file.
     * @return a List of {@link CryptoCurrencyDto} objects representing the data read from the file.
     */
    List<CryptoCurrencyDto> read(String dataPath);
}
