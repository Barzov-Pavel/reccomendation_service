package com.xm.reccomendation_service.listener;

import com.xm.reccomendation_service.dto.CryptoCurrencyDto;
import com.xm.reccomendation_service.service.Readable;
import com.xm.reccomendation_service.service.CryptoCurrency;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This class is an ApplicationListener that listens for the ApplicationReadyEvent.
 * When the event is triggered, it reads data from CSV files, saves the data into the database,
 * and initializes the application by deleting all existing data and saving new data.
 * The class is responsible for setting up the application with initial data during startup.
 */
@Component
public class ApplicationStartupListener implements ApplicationListener<ApplicationReadyEvent> {

    /**
     * The list of data paths where CSV files are located.
     */
    private static final List<String> DATA_PATHS = List.of(
            "prices/BTC_values.csv",
            "prices/DOGE_values.csv",
            "prices/ETH_values.csv",
            "prices/LTC_values.csv",
            "prices/XRP_values.csv"
    );

    private final Readable reader;

    private final CryptoCurrency cryptoCurrencyService;

    public ApplicationStartupListener(Readable reader, CryptoCurrency cryptoCurrencyService) {
        this.reader = reader;
        this.cryptoCurrencyService = cryptoCurrencyService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        cryptoCurrencyService.deleteAll();
        for (String str : DATA_PATHS) {
            List<CryptoCurrencyDto> dtos = reader.read(str);
            cryptoCurrencyService.saveAllDtos(dtos);
        }
    }
}
