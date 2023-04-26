package com.xm.reccomendation_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CryptoCurrencyStatsDto {
    private BigDecimal minValue;
    private BigDecimal maxValue;
    private BigDecimal oldestValue;
    private BigDecimal newestValue;
}
