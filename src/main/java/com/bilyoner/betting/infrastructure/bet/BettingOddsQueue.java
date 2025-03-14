package com.bilyoner.betting.infrastructure.bet;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class BettingOddsQueue {

    private final BlockingQueue<BettingOddsUpdateDto> bettingOddsUpdateDtoQueue = new LinkedBlockingQueue<>();

    public boolean offer(BettingOddsUpdateDto update) {
        return bettingOddsUpdateDtoQueue.offer(update);
    }

    public BettingOddsUpdateDto take() throws InterruptedException {
        return bettingOddsUpdateDtoQueue.take();
    }

    public int drainTo(List<BettingOddsUpdateDto> updates, int prefetchCount) {
        return bettingOddsUpdateDtoQueue.drainTo(updates, prefetchCount);
    }
}
