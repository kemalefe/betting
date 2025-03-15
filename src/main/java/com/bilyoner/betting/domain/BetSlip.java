package com.bilyoner.betting.domain;

import com.bilyoner.betting.contract.BetType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BetSlip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long customerId;
    private Long eventId;
    @Enumerated(EnumType.STRING)
    private BetType betType;
    private BigDecimal betOdds;
    private int couponCount;
    private BigDecimal betAmount;
    private String currencyCode;
    private LocalDateTime createDate;
}