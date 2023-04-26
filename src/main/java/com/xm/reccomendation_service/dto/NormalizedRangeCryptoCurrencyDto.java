package com.xm.reccomendation_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class NormalizedRangeCryptoCurrencyDto {

    private String symbol;
    private BigDecimal normalizedRange;
    private BigDecimal minValue;
    private BigDecimal maxValue;
}
