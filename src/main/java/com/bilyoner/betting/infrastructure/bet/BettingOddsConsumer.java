package com.bilyoner.betting.infrastructure.bet;

import com.bilyoner.betting.application.EventBettingOddsUpdatingService;
import com.bilyoner.betting.contract.BetOddsDto;
import com.bilyoner.betting.infrastructure.config.BettingConfig;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Profile("!test")
@RequiredArgsConstructor
@Component
@Slf4j
public class BettingOddsConsumer {

    private final BettingConfig bettingConfig;

    private final EventBettingOddsUpdatingService eventBettingOddsUpdatingService;
    private final BettingOddsQueue bettingOddsQueue;
    private final ExecutorService consumerExecutor;
    private final AtomicBoolean running = new AtomicBoolean(true);

    public void start() {

        int consumerCount = bettingConfig.getConsumer().getThreads();
        log.info("Starting {} consumer threads...", consumerCount);
        for (int i = 0; i < consumerCount; i++) {
            consumerExecutor.submit(this::startConsuming);
        }
    }

    private void startConsuming() {

        while (running.get()) {
            try {
                var updates = new ArrayList<BetOddsDto>();
                bettingOddsQueue.drainTo(updates, bettingConfig.getConsumer().getPrefetchCount());
                if (updates.isEmpty()) {
                    updates.add(bettingOddsQueue.take());
                }

                log.debug("processing {} updates...", updates.size());
                for (BetOddsDto update : updates) {
                    eventBettingOddsUpdatingService.updateBetOdds(update);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @PreDestroy
    public void shutdown() {
        log.info("Shutting down consumer threads...");
        running.set(false);
        try {
            boolean terminated = consumerExecutor.awaitTermination(10L, TimeUnit.SECONDS);
            if (!terminated)
                consumerExecutor.shutdownNow();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Handle interrupted exception:{}", e.getMessage());
        }
    }
}
