CREATE TABLE IF NOT EXISTS event (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    league_name VARCHAR(255),
    home_team VARCHAR(255),
    away_team VARCHAR(255),
    home_win_bet_odds DOUBLE,
    draw_bet_odds DOUBLE,
    away_win_bet_odds DOUBLE,
    match_start_time TIMESTAMP
);