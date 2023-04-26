package com.xm.reccomendation_service.controller;

import com.xm.reccomendation_service.dto.CryptoCurrencyStatsDto;
import com.xm.reccomendation_service.dto.NormalizedRangeCryptoCurrencyDto;
import com.xm.reccomendation_service.service.CryptoCurrency;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Tag(name = "Crypto currency controller", description = "Crypto currency management APIs")
@RestController
@RequestMapping("/api/v1/crypto-currencies")
public class CryptoCurrencyController {

    private final CryptoCurrency cryptoCurrencyService;

    public CryptoCurrencyController(CryptoCurrency cryptoCurrencyService) {
        this.cryptoCurrencyService = cryptoCurrencyService;
    }

    @Operation(
            summary = "Retrieve a Crypto currency statistics by Crypto currency name, year, month",
            description = "Get a CryptoCurrencyStatsDto object with oldest, newest, lowest and highest price in month")
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CryptoCurrencyStatsDto.class), mediaType = "application/json")})
    @GetMapping("/{cryptoCurrencyName}/stats")
    public ResponseEntity<CryptoCurrencyStatsDto> getCryptoCurrencyStats(@PathVariable String cryptoCurrencyName,
                                                                         @RequestParam Integer year,
                                                                         @RequestParam Integer month) {

        return new ResponseEntity<>(cryptoCurrencyService
                .getCryptoCurrencyStats(cryptoCurrencyName.toUpperCase(), year, month), HttpStatus.OK);
    }

    @Operation(
            summary = "Retrieve descending sorted by normalized range List of Crypto currencies",
            description = "Get a List of NormalizedRangeCryptoCurrencyDto objects descending sorted by normalized range")
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = NormalizedRangeCryptoCurrencyDto.class)))})
    @GetMapping("/sorted-by-normalized-range")
    public ResponseEntity<List<NormalizedRangeCryptoCurrencyDto>> getCryptoCurrenciesSortedByNormalizedRange() {
        return new ResponseEntity<>(cryptoCurrencyService
                .getCryptoCurrenciesSortedByNormalizedRange(), HttpStatus.OK);
    }

    @Operation(
            summary = "Retrieve a highest normalized Crypto currency by date",
            description = "Get a NormalizedRangeCryptoCurrencyDto object with highest normalized range on the date")
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = NormalizedRangeCryptoCurrencyDto.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())})
    @GetMapping("/highest-normalized-range")
    public ResponseEntity<NormalizedRangeCryptoCurrencyDto> getCryptoCurrencyWithHighestNormalizedRange(@RequestParam LocalDate date) {
        Optional<NormalizedRangeCryptoCurrencyDto> highestCryptoCurrency = cryptoCurrencyService
                .getCryptoCurrencyWithHighestNormalizedRange(date);
        return highestCryptoCurrency.map(currency -> new ResponseEntity<>(currency, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }
}
