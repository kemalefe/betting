package com.bilyoner.betting.domain.bet;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String leagueName;
    private String homeTeam;
    private String awayTeam;
    private BigDecimal homeWinBetOdds;
    private BigDecimal drawBetOdds;
    private BigDecimal awayWinBetOdds;
    private LocalDateTime matchStartTime;
}
