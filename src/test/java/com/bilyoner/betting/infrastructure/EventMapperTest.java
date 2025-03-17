package com.bilyoner.betting.infrastructure;

import com.bilyoner.betting.contract.EventDto;
import com.bilyoner.betting.domain.bet.Event;
import com.bilyoner.betting.contract.BetOddsDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class EventMapperTest {

    @Autowired
    private EventMapper eventMapper;

    @Test
    void testToDto() {
        Event event = new Event();
        event.setId(1L);
        event.setLeagueName("Premier League");
        event.setHomeTeam("Team A");
        event.setAwayTeam("Team B");
        event.setHomeWinBetOdds(new BigDecimal("1.5"));
        event.setDrawBetOdds(new BigDecimal("3.0"));
        event.setAwayWinBetOdds(new BigDecimal("2.5"));
        event.setMatchStartTime(LocalDateTime.now());

        EventDto eventDto = eventMapper.toDto(event);

        assertThat(eventDto).isNotNull();
        assertThat(eventDto.getId()).isEqualTo(event.getId());
        assertThat(eventDto.getLeagueName()).isEqualTo(event.getLeagueName());
        assertThat(eventDto.getHomeTeam()).isEqualTo(event.getHomeTeam());
        assertThat(eventDto.getAwayTeam()).isEqualTo(event.getAwayTeam());
        assertThat(eventDto.getBetOddsDto()).isNotNull();
        assertThat(eventDto.getBetOddsDto().getHomeWinBetOdds()).isEqualTo(event.getHomeWinBetOdds());
        assertThat(eventDto.getBetOddsDto().getDrawBetOdds()).isEqualTo(event.getDrawBetOdds());
        assertThat(eventDto.getBetOddsDto().getAwayWinBetOdds()).isEqualTo(event.getAwayWinBetOdds());
        assertThat(eventDto.getBetOddsDto().getEventId()).isEqualTo(event.getId());
        assertThat(eventDto.getBetOddsDto().getUpdateTimestamp()).isZero();
    }

    @Test
    void testToEntity() {
        EventDto eventDto = new EventDto();
        eventDto.setId(1L);
        eventDto.setLeagueName("Premier League");
        eventDto.setHomeTeam("Team A");
        eventDto.setAwayTeam("Team B");
        BetOddsDto betOddsDto = new BetOddsDto(1L, new BigDecimal("1.5"), new BigDecimal("3.0"), new BigDecimal("2.5"));
        eventDto.setBetOddsDto(betOddsDto);
        eventDto.setUpdateMillis(0L);
        eventDto.setMatchStartTime(LocalDateTime.now());

        Event event = eventMapper.toEntity(eventDto);

        assertThat(event).isNotNull();
        assertThat(event.getId()).isEqualTo(eventDto.getId());
        assertThat(event.getLeagueName()).isEqualTo(eventDto.getLeagueName());
        assertThat(event.getHomeTeam()).isEqualTo(eventDto.getHomeTeam());
        assertThat(event.getAwayTeam()).isEqualTo(eventDto.getAwayTeam());
        assertThat(event.getHomeWinBetOdds()).isEqualTo(eventDto.getBetOddsDto().getHomeWinBetOdds());
        assertThat(event.getDrawBetOdds()).isEqualTo(eventDto.getBetOddsDto().getDrawBetOdds());
        assertThat(event.getAwayWinBetOdds()).isEqualTo(eventDto.getBetOddsDto().getAwayWinBetOdds());
        assertThat(event.getMatchStartTime()).isNotNull();
    }
}
