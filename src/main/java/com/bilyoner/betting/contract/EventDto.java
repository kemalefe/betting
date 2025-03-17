package com.bilyoner.betting.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.EqualsExclude;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Slf4j
public class EventDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @NotNull
    private String leagueName;
    @NotNull
    private String homeTeam;
    @NotNull
    private String awayTeam;
    @NotNull
    private LocalDateTime matchStartTime;
    @NotNull
    private BetOddsDto betOddsDto;
    @EqualsExclude
    private Long updateMillis;


    public void updateBetOdds(BetOddsDto update) {
        this.updateMillis = betOddsDto.getUpdateTimestamp() != null ? System.currentTimeMillis() - betOddsDto.getUpdateTimestamp() : 0;
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
