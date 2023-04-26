package com.xm.reccomendation_service.service;


import com.xm.reccomendation_service.dto.CryptoCurrencyDto;
import com.xm.reccomendation_service.repository.CryptoCurrencyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CryptoCurrencyDatabaseService implements CryptoCurrency {

    private final CryptoCurrencyRepository repository;

    public CryptoCurrencyDatabaseService(CryptoCurrencyRepository repository) {
        this.repository = repository;
    }

    @Override
    public <T> void saveAll(List<T> dtoObjects) {
        List<com.xm.reccomendation_service.model.CryptoCurrency> cryptoCurrencies = dtoObjects
                .stream()
                .map(dtoObject -> new com.xm.reccomendation_service.model.CryptoCurrency((CryptoCurrencyDto) dtoObject))
                .toList();
        repository.saveAll(cryptoCurrencies);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

}
