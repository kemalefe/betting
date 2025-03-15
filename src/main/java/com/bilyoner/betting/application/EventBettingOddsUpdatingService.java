package com.bilyoner.betting.application;

import com.bilyoner.betting.infrastructure.bet.BetOddsDto;

public interface EventBettingOddsUpdatingService extends EventService {
    void updateBetOdds(BetOddsDto update);
}
