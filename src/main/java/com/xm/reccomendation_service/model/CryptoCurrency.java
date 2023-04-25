package com.xm.reccomendation_service.model;

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
import java.time.LocalDate;

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
    LocalDate timestamp;

    @Column
    String symbol;

    @Column
    BigDecimal price;
}
