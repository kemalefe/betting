package com.bilyoner.betting.application;

import com.bilyoner.betting.contract.EventDto;
import com.bilyoner.betting.infrastructure.bet.BetOddsDto;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventBettingOddsUpdatingServiceImpl implements InitializingBean, EventBettingOddsUpdatingService {

    private AsyncLoadingCache<Long, EventDto> caffeine;
    private final EventService eventService;

    @Override
    public void afterPropertiesSet() {
        loadCache();
    }

    public void loadCache() {
        caffeine = Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .refreshAfterWrite(1, TimeUnit.MINUTES)
                .buildAsync(eventService::getEvent);
    }

    @Override
    public List<EventDto> getAllEvents() {

        var cacheMap = caffeine.asMap();
        List<CompletableFuture<EventDto>> futures = new ArrayList<>();

        for (var entry : cacheMap.entrySet()) {
            CompletableFuture<EventDto> future = entry.getValue();
            futures.add(future);
        }

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allOf.join();

        return futures.stream()
                .map(CompletableFuture::join)
                .toList();

    }

    @Override
    public EventDto addEvent(EventDto eventDto) {
        var addedEvent = eventService.addEvent(eventDto);
        caffeine.synchronous().put(addedEvent.getId(), addedEvent);
        return addedEvent;
    }

    @Override
    public EventDto getEvent(Long eventId) {
        return caffeine.get(eventId).join();
    }

    @Override
    public EventDto updateEvent(EventDto eventDto) {
        // update cache
        caffeine.put(eventDto.getId(), CompletableFuture.supplyAsync(() -> eventDto));
        // persist - maybe later?
        return eventService.updateEvent(eventDto);
    }

    @Override
    public void updateBetOdds(BetOddsDto update) {
        var eventId = update.getEventId();
        EventDto eventDto = caffeine.get(eventId).join();
        eventDto.updateBetOdds(update);

        updateEvent(eventDto);
        log.debug("New update applied to event: {}", eventDto);
    }
}
