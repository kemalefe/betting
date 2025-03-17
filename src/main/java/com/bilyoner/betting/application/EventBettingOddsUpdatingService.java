package com.bilyoner.betting.application;

import com.bilyoner.betting.contract.BetOddsDto;

public interface EventBettingOddsUpdatingService extends EventService {
    void updateBetOdds(BetOddsDto update);
}
