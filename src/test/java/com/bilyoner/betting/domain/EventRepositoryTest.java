package com.bilyoner.betting.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Test
    void testCreateAndFindEvent() {
        Event event = new Event();
        event.setLeagueName("Premier League");
        event.setHomeTeam("Team A");
        event.setAwayTeam("Team B");
        event.setHomeWinBetOdds(new BigDecimal("1.5"));
        event.setDrawBetOdds(new BigDecimal("2.0"));
        event.setAwayWinBetOdds(new BigDecimal("2.5"));
        event.setMatchStartTime(LocalDateTime.now());

        Event savedEvent = eventRepository.save(event);

        assertThat(savedEvent).isNotNull();
        assertThat(savedEvent.getId()).isGreaterThan(0);
        assertThat(savedEvent.getLeagueName()).isEqualTo("Premier League");
        assertThat(savedEvent.getHomeTeam()).isEqualTo("Team A");
        assertThat(savedEvent.getAwayTeam()).isEqualTo("Team B");
        assertThat(savedEvent.getHomeWinBetOdds()).isEqualByComparingTo(new BigDecimal("1.5"));
        assertThat(savedEvent.getMatchStartTime()).isNotNull();

        Event foundEvent = eventRepository.findById(savedEvent.getId()).orElse(null);
        assertThat(foundEvent).isNotNull();
        assertThat(foundEvent.getId()).isEqualTo(savedEvent.getId());
    }
}
