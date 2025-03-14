package com.bilyoner.betting;

import com.bilyoner.betting.infrastructure.bet.BettingOddsConsumer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BettingQueueListener implements CommandLineRunner {

    private final BettingOddsConsumer bettingOddsConsumer;

    @Override
    public void run(String... args) {
        bettingOddsConsumer.start();
    }
}
