package com.bilyoner.betting.contract;

import com.bilyoner.betting.infrastructure.bet.BetOddsDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private LocalDateTime matchStartTime;
    private BetOddsDto betOddsDto;

    public void updateBetOdds(BetOddsDto update) {
        this.betOddsDto = new BetOddsDto(id, update.getHomeWinBetOdds(), update.getDrawBetOdds(), update.getAwayWinBetOdds());
    }

    @JsonIgnore
    public BigDecimal getBetOdds(BetType betType) {
        if (betOddsDto == null) {
            throw new IllegalStateException("Bet odds of event not exists.");
        }

        return switch (betType) {
            case AWAY_WIN -> betOddsDto.getAwayWinBetOdds();
            case DRAW -> betOddsDto.getDrawBetOdds();
            case HOME_WIN -> betOddsDto.getHomeWinBetOdds();
        };
    }
}
