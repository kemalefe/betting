package com.bilyoner.betting.infrastructure.bet;

import com.bilyoner.betting.contract.BetOddsDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Profile("!test")
@Component
public class BettingOddsQueue {

    private final BlockingQueue<BetOddsDto> betOddsDtoQueue = new LinkedBlockingQueue<>();

    public boolean offer(BetOddsDto update) {
        return betOddsDtoQueue.offer(update);
    }

    public BetOddsDto take() throws InterruptedException {
        return betOddsDtoQueue.take();
    }

    public int drainTo(List<BetOddsDto> updates, int prefetchCount) {
        return betOddsDtoQueue.drainTo(updates, prefetchCount);
    }
}
