package com.bilyoner.betting.contract;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class EventDto {

    private String leagueName;
    private String homeTeam;
    private String awayTeam;
    private BigDecimal homeWinBetOdds;
    private BigDecimal drawBetOdds;
    private BigDecimal awayWinBetOdds;
    private LocalDateTime matchStartTime;
}
