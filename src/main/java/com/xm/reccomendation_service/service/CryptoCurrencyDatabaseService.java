package com.xm.reccomendation_service.service;


import com.xm.reccomendation_service.dto.CryptoCurrencyDto;
import com.xm.reccomendation_service.model.CryptoCurrency;
import com.xm.reccomendation_service.repository.CryptoCurrencyRepository;

import java.util.List;

public class CryptoCurrencyDatabaseService implements Savable {

    private final CryptoCurrencyRepository repository;

    public CryptoCurrencyDatabaseService(CryptoCurrencyRepository repository) {
        this.repository = repository;
    }

    public <T> void saveAll(List<T> dtoObjects) {
        List<CryptoCurrency> cryptoCurrencies = dtoObjects
                .stream()
                .map(dtoObject -> new CryptoCurrency((CryptoCurrencyDto) dtoObject))
                .toList();
        repository.saveAll(cryptoCurrencies);
    }
}
