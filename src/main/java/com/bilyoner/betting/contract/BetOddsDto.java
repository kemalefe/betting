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
}
