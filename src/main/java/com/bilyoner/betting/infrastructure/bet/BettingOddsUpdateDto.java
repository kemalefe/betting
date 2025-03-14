package com.bilyoner.betting.infrastructure.bet;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ToString
@Getter
@Setter
public class BettingOddsUpdateDto {

    private Long eventId;
    private BigDecimal homeWinBetOdds;
    private BigDecimal drawBetOdds;
    private BigDecimal awayWinBetOdds;
    private LocalDateTime updateDateTime;

    public BettingOddsUpdateDto(Long eventId, BigDecimal homeWinBetOdds, BigDecimal drawBetOdds, BigDecimal awayWinBetOdds) {
        this.eventId = eventId;
        this.homeWinBetOdds = homeWinBetOdds;
        this.drawBetOdds = drawBetOdds;
        this.awayWinBetOdds = awayWinBetOdds;
        this.updateDateTime = LocalDateTime.now();
    }
}
