package com.bilyoner.betting.contract;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class BetDto {
    private Long id;
    private Long betSlipId;
    private Long eventId;
    private BetType betType;
    private BigDecimal betAmount;
    private String currencyCode;
}