package com.xm.reccomendation_service.service;

import com.opencsv.bean.CsvToBeanBuilder;
import com.xm.reccomendation_service.dto.CryptoCurrencyDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.util.Collections;
import java.util.List;

public class ReadCsvFileService implements Readable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadCsvFileService.class);

    @Override
    public List<CryptoCurrencyDto> read(String dataPath) {
        List<CryptoCurrencyDto> cryptoCurrencyDtos = Collections.emptyList();


        try {
            cryptoCurrencyDtos = new CsvToBeanBuilder<CryptoCurrencyDto>(
                    new FileReader(dataPath))
                    .withType(CryptoCurrencyDto.class)
                    .build()
                    .parse();
        } catch (Exception e) {
            LOGGER.error("Error reading data by path: {} \nReason:\n {}",
                    dataPath, e.getMessage());
        }
        return cryptoCurrencyDtos;
    }
}
