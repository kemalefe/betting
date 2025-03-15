package com.bilyoner.betting.domain.exception;

public class EventNotFoundException extends Exception {

    public EventNotFoundException(Long eventId) {
        super("Event id: %s not found".formatted(eventId));
    }
}
