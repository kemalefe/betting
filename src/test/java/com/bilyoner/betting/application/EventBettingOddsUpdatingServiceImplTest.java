package com.bilyoner.betting.application;

import com.bilyoner.betting.contract.EventDto;
import com.bilyoner.betting.contract.BetOddsDto;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class EventBettingOddsUpdatingServiceImplTest {

    @MockitoBean
    private EventService eventService;
    @MockitoBean
    private AsyncLoadingCache<Long, EventDto> asyncCaffeine;
    @MockitoBean
    private LoadingCache<Long, EventDto> syncCaffeine;
    @Autowired
    @InjectMocks
    private EventBettingOddsUpdatingServiceImpl eventBettingOddsUpdatingService;

    private EventDto eventDto;
    private BetOddsDto betOddsDto;
    private ConcurrentMap<Long, CompletableFuture<EventDto>> cacheMap;

    @BeforeEach
    void setUp() {
        eventDto = getEventDto();
        betOddsDto = new BetOddsDto(1L, BigDecimal.valueOf(1.8), BigDecimal.valueOf(2.3), BigDecimal.valueOf(1.6));
        cacheMap = new ConcurrentHashMap<>();
        MockitoAnnotations.openMocks(this);
    }

    private EventDto getEventDto() {
        EventDto event = new EventDto();
        event.setId(10L);
        event.setHomeTeam("Fenerbahçe");
        event.setAwayTeam("Galatasaray");
        event.setLeagueName("Turkish Super League");
        event.setBetOddsDto(new BetOddsDto(1L, BigDecimal.valueOf(1.5), BigDecimal.valueOf(2.21), BigDecimal.valueOf(1.47)));
        event.setMatchStartTime(LocalDateTime.of(2025, 7, 13, 21, 45));
        return event;
    }

    @Test
    void shouldLoadCacheOnInitialization() {
        cacheMap.put(10L, CompletableFuture.completedFuture(eventDto));
        when(asyncCaffeine.asMap()).thenReturn(cacheMap);
        eventBettingOddsUpdatingService.afterPropertiesSet();

        assertThat(asyncCaffeine.asMap()).containsKey(10L);
        assertThat(asyncCaffeine.asMap().get(10L).join()).isEqualTo(eventDto);
    }

    @Test
    void shouldGetAllEventsFromCache() {
        cacheMap.put(10L, CompletableFuture.completedFuture(eventDto));
        when(asyncCaffeine.asMap()).thenReturn(cacheMap);

        List<EventDto> events = eventBettingOddsUpdatingService.getAllEvents();

        assertThat(events).hasSize(1);
        assertThat(events.getFirst().getId()).isEqualTo(10L);
        assertThat(events.getFirst().getHomeTeam()).isEqualTo("Fenerbahçe");
    }

    @Test
    void shouldAddEventAndUpdateCache() {
        when(eventService.addEvent(any())).thenReturn(eventDto);
        when(asyncCaffeine.synchronous()).thenReturn(syncCaffeine);

        EventDto addedEvent = eventBettingOddsUpdatingService.addEvent(eventDto);

        verify(syncCaffeine).put(eq(10L), eq(eventDto));
        assertThat(addedEvent).isEqualTo(eventDto);
    }

    @Test
    void shouldGetEventFromCache() {
        when(asyncCaffeine.get(10L)).thenReturn(CompletableFuture.completedFuture(eventDto));
        EventDto result = eventBettingOddsUpdatingService.getEvent(10L);
        assertThat(result).isEqualTo(eventDto);
    }

    @Test
    void shouldUpdateEventAndCache() {
        when(eventService.updateEvent(any())).thenReturn(eventDto);

        EventDto updatedEvent = eventBettingOddsUpdatingService.updateEvent(eventDto);

        verify(asyncCaffeine).put(eq(10L), any(CompletableFuture.class));
        assertThat(updatedEvent).isEqualTo(eventDto);
    }

    @Test
    void shouldUpdateBetOdds() {
        when(asyncCaffeine.get(anyLong())).thenReturn(CompletableFuture.supplyAsync(() -> eventDto));
        when(eventService.updateEvent(any())).thenReturn(eventDto);

        eventBettingOddsUpdatingService.updateBetOdds(betOddsDto);

        assertThat(eventDto.getBetOddsDto().getHomeWinBetOdds()).isEqualTo(BigDecimal.valueOf(1.8));
        assertThat(eventDto.getBetOddsDto().getAwayWinBetOdds()).isEqualTo(BigDecimal.valueOf(1.6));
    }
}
