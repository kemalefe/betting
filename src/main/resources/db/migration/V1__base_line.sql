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

CREATE TABLE bet_slip (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    customer_id BIGINT NOT NULL,
    event_id BIGINT NOT NULL,
    bet_type VARCHAR(50) NOT NULL,
    bet_odds DECIMAL(10,2) NOT NULL,
    coupon_count INT NOT NULL,
    bet_amount DECIMAL(10,2) NOT NULL,
    currency_code VARCHAR(10) NOT NULL,
    create_date TIMESTAMP NOT NULL
);