package com.bilyoner.betting.application;

import com.bilyoner.betting.infrastructure.bet.BettingOddsUpdateDto;

public interface EventBettingOddsUpdatingService extends EventService {
    void updateBetOdds(BettingOddsUpdateDto update);
}
