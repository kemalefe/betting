package com.bilyoner.betting.contract;

import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BetOddsDto {

    @Positive
    private Long eventId;
    @Positive
    private BigDecimal homeWinBetOdds;
    @Positive
    private BigDecimal drawBetOdds;
    @Positive
    private BigDecimal awayWinBetOdds;
    @EqualsAndHashCode.Exclude
    private Long updateTimestamp;

    public BetOddsDto(Long eventId, BigDecimal homeWinBetOdds, BigDecimal drawBetOdds, BigDecimal awayWinBetOdds) {
        this.eventId = eventId;
        this.homeWinBetOdds = homeWinBetOdds;
        this.drawBetOdds = drawBetOdds;
        this.awayWinBetOdds = awayWinBetOdds;
        this.updateTimestamp = System.currentTimeMillis();
    }
}
