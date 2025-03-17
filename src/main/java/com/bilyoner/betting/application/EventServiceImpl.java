package com.bilyoner.betting.application;

import com.bilyoner.betting.contract.EventDto;
import com.bilyoner.betting.domain.bet.Event;
import com.bilyoner.betting.domain.bet.EventRepository;
import com.bilyoner.betting.domain.exception.EventNotFoundException;
import com.bilyoner.betting.infrastructure.mapper.EventMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public List<EventDto> getAllEvents() {
        List<Event> allEvents = eventRepository.findAll();
        return eventMapper.toDto(allEvents);
    }

    @Override
    public EventDto addEvent(EventDto eventDto) {
        Event eventEntity = eventMapper.toEntity(eventDto);
        eventEntity = eventRepository.save(eventEntity);
        return eventMapper.toDto(eventEntity);
    }

    @Override
    public EventDto getEvent(Long eventId) {
        var eventEntity = getEventEntity(eventId);
        return eventMapper.toDto(eventEntity);
    }

    @Override
    public EventDto updateEvent(EventDto eventDto) {
        var eventEntity = getEventEntity(eventDto.getId());
        eventMapper.partialUpdate(eventEntity, eventDto);
        eventEntity = eventRepository.save(eventEntity);
        return eventMapper.toDto(eventEntity);
    }

    @SneakyThrows
    private Event getEventEntity(Long eventId) {
        var optional = eventRepository.findById(eventId);
        return optional.orElseThrow(() -> new EventNotFoundException(eventId));
    }
}
