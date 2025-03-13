package com.bilyoner.betting.domain;

import com.bilyoner.betting.contract.BetType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Bet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long betSlipId;
    private Long eventId;

    @Enumerated(EnumType.STRING)
    private BetType betType;
    private BigDecimal betAmount;
    private String currencyCode;
}