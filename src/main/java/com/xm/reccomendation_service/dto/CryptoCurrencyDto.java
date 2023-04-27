package com.xm.reccomendation_service.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * A data transfer object class representing a cryptocurrency with its timestamp, symbol and price.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CryptoCurrencyDto {

    @CsvBindByName(column = "timestamp")
    long timestamp;

    @CsvBindByName(column = "symbol")
    String symbol;

    @CsvBindByName(column = "price")
    BigDecimal price;
}
