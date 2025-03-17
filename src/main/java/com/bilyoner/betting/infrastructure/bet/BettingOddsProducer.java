package com.bilyoner.betting.infrastructure.bet;

import com.bilyoner.betting.contract.BetOddsDto;
import com.bilyoner.betting.domain.bet.Event;
import com.bilyoner.betting.domain.bet.EventRepository;
import com.bilyoner.betting.infrastructure.config.BettingConfig;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.IntStream;

@Profile("!test")
@Slf4j
@Component
@RequiredArgsConstructor
public class BettingOddsProducer {

    private final BettingConfig bettingConfig;
    private final BettingOddsQueue bettingOddsQueue;
    private final EventRepository eventRepository;
    private final ExecutorService producerExecutor;
    private final Random random = new Random();

    @Scheduled(fixedRateString = "${betting.odds.update-interval}")
    public void start() {
        log.info("Starting {} producer threads...", bettingConfig.getProducer().getThreads());
        updateBetOdds();
    }

    private void updateBetOdds() {
        List<Event> allEvents = eventRepository.findAll();
        if (allEvents.isEmpty()) return;

        int producerCount = bettingConfig.getProducer().getThreads();
        int batchSize = Math.ceilDiv(allEvents.size(), producerCount);
        IntStream.range(0, producerCount).forEach(i -> CompletableFuture.runAsync(() -> {
            int start = i * batchSize;
            int end = Math.min(start + batchSize, allEvents.size());

            var batchEvents = new ArrayList<>(allEvents.subList(start, end));
            while (!batchEvents.isEmpty()) {
                var selectRandomEvent = selectRandomEvent(batchEvents);
                updateEventBettingOdds(selectRandomEvent.getId());
            }
        }, producerExecutor));
    }

    private void updateEventBettingOdds(Long eventId) {
        var bettingOddsUpdate = new BetOddsDto(eventId, randomOdds(), randomOdds(), randomOdds());
        bettingOddsQueue.offer(bettingOddsUpdate);
    }

    private Event selectRandomEvent(List<Event> events) {
        int index = random.nextInt(events.size());
        return events.remove(index);
    }

    private BigDecimal randomOdds() {
        var odds = bettingConfig.getOdds();
        double randomOdds = odds.getMinValue() + (odds.getMaxValue() - odds.getMinValue()) * random.nextDouble();
        return BigDecimal.valueOf(randomOdds).setScale(2, RoundingMode.HALF_UP);
    }

    @PreDestroy
    public void shutdown() {
        log.info("Shutting down producer threads...");
        producerExecutor.shutdownNow();
    }
}
