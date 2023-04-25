package com.xm.reccomendation_service.model;

import com.xm.reccomendation_service.dto.CryptoCurrencyDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

@Entity
@Table(name = "crypto_currencies")
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CryptoCurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column
    LocalDateTime timestamp;

    @Column
    String symbol;

    @Column
    BigDecimal price;

    public CryptoCurrency(CryptoCurrencyDto currencyDto) {
        this(
                null,
                LocalDateTime.ofInstant(Instant.ofEpochMilli(currencyDto.getTimestamp()),
                        TimeZone.getDefault().toZoneId()),
                currencyDto.getSymbol(),
                currencyDto.getPrice()
        );
    }
}
