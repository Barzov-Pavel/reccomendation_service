package com.xm.reccomendation_service.service;

import com.xm.reccomendation_service.dto.CryptoCurrencyDto;

import java.util.List;

public interface Readable {
    /**
     * @param dataPath Path to data which need to read
     * @return List of CryptoCurrencyDto with read data
     */
    List<CryptoCurrencyDto> read(String dataPath);
}
