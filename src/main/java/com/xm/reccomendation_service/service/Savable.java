package com.xm.reccomendation_service.service;

import java.util.List;

public interface Savable {

    <T> void saveAll(List<T> dtoObjects);
}
