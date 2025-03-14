package com.bilyoner.betting.application;

import com.bilyoner.betting.contract.EventDto;

import java.util.List;

public interface EventService {
    List<EventDto> getAllEvents();

    EventDto addEvent(EventDto eventDto);

    EventDto getEvent(Long eventId);

    EventDto updateEvent(EventDto eventDto);
}
