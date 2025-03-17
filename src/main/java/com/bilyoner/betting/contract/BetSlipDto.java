package com.bilyoner.betting.contract;

import com.bilyoner.betting.infrastructure.bet.ValidBetSlip;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@ValidBetSlip
public class BetSlipDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long customerId;
    @NotNull
    @Positive
    private Long eventId;
    @NotNull
    private BetType betType;
    @NotNull
    private BigDecimal betOdds;
    @Positive
    private int couponCount;
    @Positive
    private BigDecimal betAmount;
    @NotNull
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
}