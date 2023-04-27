package com.xm.reccomendation_service.listener;

import com.xm.reccomendation_service.dto.CryptoCurrencyDto;
import com.xm.reccomendation_service.service.Readable;
import com.xm.reccomendation_service.service.CryptoCurrency;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationStartupListener implements ApplicationListener<ApplicationReadyEvent> {

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
