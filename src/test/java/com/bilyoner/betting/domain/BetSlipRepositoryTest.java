package com.bilyoner.betting.domain;

import com.bilyoner.betting.contract.BetType;
import com.bilyoner.betting.domain.bet.BetSlip;
import com.bilyoner.betting.domain.bet.BetSlipRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BetSlipRepositoryTest {

    @Autowired
    private BetSlipRepository betSlipRepository;

    private BetSlip betSlip;

    @BeforeEach
    void setUp() {
        betSlip = new BetSlip();

        betSlip.setCustomerId(123L);
        betSlip.setEventId(456L);
        betSlip.setBetType(BetType.HOME_WIN);
        betSlip.setBetOdds(BigDecimal.valueOf(2.5));
        betSlip.setCouponCount(1);
        betSlip.setBetAmount(BigDecimal.valueOf(100));
        betSlip.setCurrencyCode("USD");
        betSlip.setCreateDate(LocalDateTime.now());
    }

    @Test
    void testSaveBetSlip() {
        BetSlip savedBetSlip = betSlipRepository.save(betSlip);

        assertThat(savedBetSlip).isNotNull();
        assertThat(savedBetSlip.getCustomerId()).isEqualTo(123L);
        assertThat(savedBetSlip.getBetAmount()).isEqualTo(BigDecimal.valueOf(100));
        assertThat(savedBetSlip.getBetType()).isEqualTo(BetType.HOME_WIN);
    }
}