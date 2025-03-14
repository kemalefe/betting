package com.bilyoner.betting.contract;

import com.bilyoner.betting.infrastructure.bet.BettingOddsUpdateDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Slf4j
public class EventDto {

    private Long id;
    private String leagueName;
    private String homeTeam;
    private String awayTeam;
    private BigDecimal homeWinBetOdds;
    private BigDecimal drawBetOdds;
    private BigDecimal awayWinBetOdds;
    private LocalDateTime matchStartTime;
    private Long updateMilliseconds;
    private Long lastUpdateTimestamp;

    public void updateBetOdds(BettingOddsUpdateDto update) {
        this.homeWinBetOdds = update.getHomeWinBetOdds();
        this.drawBetOdds = update.getDrawBetOdds();
        this.awayWinBetOdds = update.getAwayWinBetOdds();
        this.updateMilliseconds = lastUpdateTimestamp != null ? (System.currentTimeMillis() - lastUpdateTimestamp) : 0;
        this.lastUpdateTimestamp = System.currentTimeMillis();

        if (updateMilliseconds > 1200)
            log.error("UNACCEPTABLE UPDATE INTERVAL!!! {}", this);
    }
}
