package com.bilyoner.betting.application;

import com.bilyoner.betting.contract.EventDto;
import com.bilyoner.betting.domain.Event;
import com.bilyoner.betting.domain.EventRepository;
import com.bilyoner.betting.infrastructure.mapper.EventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public List<EventDto> fetchEvents() {
        List<Event> allEvents = eventRepository.findAll();
        return eventMapper.toDto(allEvents);
    }
}
