package com.bilyoner.betting.protocol;

import com.bilyoner.betting.application.EventBettingOddsUpdatingService;
import com.bilyoner.betting.contract.EventDto;
import com.bilyoner.betting.contract.BetOddsDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EventController.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventBettingOddsUpdatingService eventService;

    private final ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder().build();

    @Test
    void shouldGetAllEvents() throws Exception {
        List<EventDto> events = List.of(getEventDto());
        when(eventService.getAllEvents()).thenReturn(events);

        mockMvc.perform(get("/api/events")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(events.size()))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].homeTeam").value("Fenerbahçe"));

        verify(eventService).getAllEvents();
    }

    @Test
    void shouldGetEventById() throws Exception {
        EventDto event = new EventDto();
        event.setId(1L);
        event.setHomeTeam("Fenerbahçe");
        event.setAwayTeam("Galatasaray");
        event.setLeagueName("Turkish Super League");
        when(eventService.getEvent(1L)).thenReturn(event);

        mockMvc.perform(get("/api/events/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.homeTeam").value("Fenerbahçe"))
                .andExpect(jsonPath("$.awayTeam").value("Galatasaray"));

        verify(eventService).getEvent(1L);
    }

    @Test
    void shouldAddEvent() throws Exception {

        EventDto event = getEventDto();
        EventDto savedEvent = getEventDto();

        when(eventService.addEvent(any(EventDto.class))).thenReturn(savedEvent);

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.homeTeam").value("Fenerbahçe"))
                .andExpect(jsonPath("$.awayTeam").value("Galatasaray"));

        verify(eventService).addEvent(any(EventDto.class));
    }

    private static EventDto getEventDto() {
        EventDto event = new EventDto();
        event.setId(1L);
        event.setHomeTeam("Fenerbahçe");
        event.setAwayTeam("Galatasaray");
        event.setLeagueName("Turkish Super League");
        event.setBetOddsDto(new BetOddsDto(1L, BigDecimal.valueOf(1.5), BigDecimal.valueOf(2.21), BigDecimal.valueOf(1.45)));
        event.setMatchStartTime(LocalDateTime.of(2025, 7, 13, 21, 45));
        return event;
    }
}
