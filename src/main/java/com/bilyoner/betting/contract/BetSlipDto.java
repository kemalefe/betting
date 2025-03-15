package com.bilyoner.betting.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
@NoArgsConstructor
public class BetSlipDto {

    private Long id;
    private Long customerId;
    private Long eventId;
    private BetType betType;
    private BigDecimal betOdds;
    private int couponCount;
    private BigDecimal betAmount;
    private String currencyCode;
    @JsonIgnore
    private Long timestamp;

    public BetSlipDto(BetSlipDto other) {

        this.id = other.getId();
        this.customerId = other.getCustomerId();
        this.eventId = other.getEventId();
        this.betType = other.getBetType();
        this.betOdds = other.getBetOdds();
        this.couponCount = other.getCouponCount();
        this.betAmount = other.getBetAmount();
        this.currencyCode = other.getCurrencyCode();
        this.timestamp = other.getTimestamp();
    }

    public LocalDateTime getCreateDate() {
        return Instant.ofEpochMilli(this.timestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}