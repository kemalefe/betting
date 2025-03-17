package com.bilyoner.betting.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long customerId;
    @Positive
    private Long eventId;
    @NotNull
    private BetType betType;
    @NotNull
    private BigDecimal betOdds;
    @Positive
    @Max(value = 500, message = "Coupon count could not be gt 500 for single bet slip.")
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