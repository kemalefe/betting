package com.bilyoner.betting.infrastructure.bet;

import lombok.*;

import java.math.BigDecimal;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BetOddsDto {

    private Long eventId;
    private BigDecimal homeWinBetOdds;
    private BigDecimal drawBetOdds;
    private BigDecimal awayWinBetOdds;
    private Long updateTimestamp;

    public BetOddsDto(Long eventId, BigDecimal homeWinBetOdds, BigDecimal drawBetOdds, BigDecimal awayWinBetOdds) {
        this.eventId = eventId;
        this.homeWinBetOdds = homeWinBetOdds;
        this.drawBetOdds = drawBetOdds;
        this.awayWinBetOdds = awayWinBetOdds;
        this.updateTimestamp = System.currentTimeMillis();
    }
}
