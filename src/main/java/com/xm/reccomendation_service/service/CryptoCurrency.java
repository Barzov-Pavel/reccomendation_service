package com.xm.reccomendation_service.service;

import java.util.List;

public interface CryptoCurrency {

    <T> void saveAll(List<T> dtoObjects);

    void deleteAll();
}
