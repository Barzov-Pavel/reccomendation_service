package com.xm.reccomendation_service.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
