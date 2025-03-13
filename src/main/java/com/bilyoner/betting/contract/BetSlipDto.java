package com.bilyoner.betting.contract;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class BetSlipDto {
    private Long id;
    private Long customerId;
    private BigDecimal totalBetAmount;
    private String currencyCode;
    private Integer numberOfBets;
}