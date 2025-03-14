package com.bilyoner.betting.protocol;

import com.bilyoner.betting.application.EventBettingOddsUpdatingService;
import com.bilyoner.betting.contract.EventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api/events", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class EventController {

    private final EventBettingOddsUpdatingService eventService;

    @GetMapping
    public List<EventDto> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public EventDto getEvent(@PathVariable Long id) {
        return eventService.getEvent(id);
    }

    @PostMapping
    public EventDto add(@RequestBody EventDto event) {
        return eventService.addEvent(event);
    }
}
