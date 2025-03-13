package com.bilyoner.betting.contract;

import java.util.stream.Stream;

public enum BetType {
    HOME_WIN,
    AWAY_WIN,
    DRAW;

    public static BetType of(String name) {
        return Stream.of(BetType.values()).filter(p -> p.name().equals(name)).findFirst().orElse(null);
    }
}
