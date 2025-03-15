package com.bilyoner.betting.infrastructure.bet;

import com.bilyoner.betting.application.EventBettingOddsUpdatingService;
import com.bilyoner.betting.infrastructure.config.BettingConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

@RequiredArgsConstructor
@Component
@Slf4j
public class BettingOddsConsumer {

    private final BettingConfig bettingConfig;

    private final EventBettingOddsUpdatingService eventBettingOddsUpdatingService;
    private final BettingOddsQueue bettingOddsQueue;
    private final ExecutorService consumerExecutor;

    public void start() {

        int consumerCount = bettingConfig.getConsumer().getThreads();
        log.info("Starting {} consumer threads...", consumerCount);
        for (int i = 0; i < consumerCount; i++) {
            consumerExecutor.submit(this::startConsuming);
        }
    }

    private void startConsuming() {

        while (true) {
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
            } catch (Exception e) {
                handleException(e);
            }
        }
    }

    private static void handleException(Exception e) {
        if (e instanceof InterruptedException)
            Thread.currentThread().interrupt();

        log.error("Error occurred while listening bet odds streaming queue", e);
    }
}
