package com.bilyoner.betting.application;

import com.bilyoner.betting.contract.EventDto;

import java.util.List;

public interface EventService {
    public List<EventDto> fetchEvents();
}
