package com.bilyoner.betting.application;

import com.bilyoner.betting.contract.BetOddsDto;
import com.bilyoner.betting.contract.EventDto;
import com.bilyoner.betting.domain.bet.Event;
import com.bilyoner.betting.domain.bet.EventRepository;
import com.bilyoner.betting.domain.exception.EventNotFoundException;
import com.bilyoner.betting.infrastructure.EventMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventMapper eventMapper;

    @InjectMocks
    private EventServiceImpl eventService;

    private Event eventEntity;
    private EventDto eventDto;

    @BeforeEach
    void setUp() {
        eventDto = getEventDto();
        eventEntity = getEventEntity();
    }

    private static EventDto getEventDto() {
        EventDto event = new EventDto();
        event.setId(10L);
        event.setHomeTeam("Fenerbahçe");
        event.setAwayTeam("Galatasaray");
        event.setLeagueName("Turkish Super League");
        event.setBetOddsDto(new BetOddsDto(1L, BigDecimal.valueOf(1.5), BigDecimal.valueOf(2.21), BigDecimal.valueOf(1.45)));
        event.setMatchStartTime(LocalDateTime.of(2025, 7, 13, 21, 45));
        return event;
    }

    private static Event getEventEntity() {
        Event event = new Event();
        event.setId(10L);
        event.setHomeTeam("Fenerbahçe");
        event.setAwayTeam("Galatasaray");
        event.setLeagueName("Turkish Super League");
        event.setDrawBetOdds(BigDecimal.valueOf(2.21));
        event.setAwayWinBetOdds(BigDecimal.valueOf(1.45));
        event.setHomeWinBetOdds(BigDecimal.valueOf(1.5));
        event.setMatchStartTime(LocalDateTime.of(2025, 7, 13, 21, 45));
        return event;
    }

    @Test
    void shouldGetAllEvents() {

        List<Event> eventList = List.of(eventEntity);
        List<EventDto> eventDtoList = List.of(eventDto);

        when(eventRepository.findAll()).thenReturn(eventList);
        when(eventMapper.toDto(eventList)).thenReturn(eventDtoList);

        List<EventDto> result = eventService.getAllEvents();

        assertThat(result).isNotNull().hasSize(1);
        assertThat(result.getFirst().getId()).isEqualTo(10L);
        assertThat(result.getFirst().getHomeTeam()).isEqualTo("Fenerbahçe");
        assertThat(result.getFirst().getAwayTeam()).isEqualTo("Galatasaray");
        assertThat(result.getFirst().getLeagueName()).isEqualTo("Turkish Super League");
        assertThat(result.getFirst().getBetOddsDto().getHomeWinBetOdds()).isEqualTo(BigDecimal.valueOf(1.5));
    }

    @Test
    void shouldAddEvent() {

        when(eventMapper.toEntity(eventDto)).thenReturn(eventEntity);
        when(eventRepository.save(eventEntity)).thenReturn(eventEntity);
        when(eventMapper.toDto(eventEntity)).thenReturn(eventDto);

        EventDto result = eventService.addEvent(eventDto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(10L);
        assertThat(result.getHomeTeam()).isEqualTo("Fenerbahçe");
        assertThat(result.getAwayTeam()).isEqualTo("Galatasaray");
        assertThat(result.getLeagueName()).isEqualTo("Turkish Super League");
    }

    @Test
    void shouldGetEvent() {

        when(eventRepository.findById(10L)).thenReturn(Optional.of(eventEntity));
        when(eventMapper.toDto(eventEntity)).thenReturn(eventDto);

        EventDto result = eventService.getEvent(10L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(10L);
        assertThat(result.getHomeTeam()).isEqualTo("Fenerbahçe");
        assertThat(result.getAwayTeam()).isEqualTo("Galatasaray");
        assertThat(result.getLeagueName()).isEqualTo("Turkish Super League");
        assertThat(result.getMatchStartTime()).isEqualTo(LocalDateTime.of(2025, 7, 13, 21, 45));
    }

    @Test
    void shouldUpdateEvent() {

        when(eventRepository.findById(10L)).thenReturn(Optional.of(eventEntity));
        when(eventRepository.save(eventEntity)).thenReturn(eventEntity);
        when(eventMapper.toDto(eventEntity)).thenReturn(eventDto);

        EventDto result = eventService.updateEvent(eventDto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(10L);
        assertThat(result.getHomeTeam()).isEqualTo("Fenerbahçe");
        assertThat(result.getAwayTeam()).isEqualTo("Galatasaray");
        assertThat(result.getLeagueName()).isEqualTo("Turkish Super League");
    }

    @Test
    void shouldThrowExceptionWhenEventNotFound() {

        when(eventRepository.findById(10L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> eventService.getEvent(10L))
                .isInstanceOf(EventNotFoundException.class)
                .hasMessage("Event id: 10 not found");
    }
}
