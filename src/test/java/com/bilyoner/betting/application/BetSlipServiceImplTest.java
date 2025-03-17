package com.bilyoner.betting.application;

import com.bilyoner.betting.contract.*;
import com.bilyoner.betting.domain.BetSlip;
import com.bilyoner.betting.domain.BetSlipRepository;
import com.bilyoner.betting.domain.core.CustomerDto;
import com.bilyoner.betting.domain.exception.BetSlipExpiredException;
import com.bilyoner.betting.contract.BetOddsDto;
import com.bilyoner.betting.infrastructure.config.BettingConfig;
import com.bilyoner.betting.infrastructure.mapper.BetSlipMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BetSlipServiceImplTest {

    @Mock
    private BettingConfig bettingConfig;

    @Mock
    private EventBettingOddsUpdatingService eventBettingOddsUpdatingService;

    @Mock
    private BetSlipRepository betSlipRepository;

    @Mock
    private BetSlipMapper betSlipMapper;

    @InjectMocks
    private BetSlipServiceImpl betSlipService;

    @BeforeEach
    void setUp() {
        betSlipService.loadCache();
    }

    @Test
    void shouldInitializeBetSlip() {

        CustomerDto customer = new CustomerDto(1L, "Kemal", "Efe");

        BetSlipDto betSlipDto = getBetSlipDto();
        EventDto eventDto = getEventDto();

        when(eventBettingOddsUpdatingService.getEvent(10L)).thenReturn(eventDto);
        when(bettingConfig.getBetFinalizeTimeout()).thenReturn(60L);

        BetSlipInitializeResponse response = betSlipService.initializeBetSlip(customer, betSlipDto);

        assertThat(response).isNotNull();
        assertThat(response.getTimeoutSeconds()).isEqualTo(60L);
        assertThat(response.getInquiryId()).isNotNull();
        assertThat(response.getBetSlipDto().getBetOdds()).isNotNull().isEqualTo(new BigDecimal("2.21"));
    }

    @Test
    void shouldFinalizeBetSlipSuccessfully() throws BetSlipExpiredException {
        CustomerDto customer = new CustomerDto(1L, "Kemal", "Efe");
        BetSlipDto betSlipDto = getBetSlipDto();

        EventDto eventDto = getEventDto();
        when(eventBettingOddsUpdatingService.getEvent(10L)).thenReturn(eventDto);

        BetSlipInitializeResponse initializeBetSlip = betSlipService.initializeBetSlip(customer, betSlipDto);

        when(bettingConfig.getBetFinalizeTimeout()).thenReturn(2000L);
        when(betSlipMapper.toEntity(any(), anyLong())).thenReturn(new BetSlip());
        when(betSlipRepository.save(any())).thenReturn(new BetSlip());
        when(betSlipMapper.toDto(any(BetSlip.class))).thenReturn(betSlipDto);

        BetSlipFinalizeResponse response = betSlipService.finalizeBetSlip(customer, initializeBetSlip.getInquiryId());

        assertThat(response).isNotNull();
        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getBetSlipDto().getBetOdds()).isEqualTo(new BigDecimal("2.21"));
    }

    @Test
    void shouldThrowExceptionWhenFinalizingExpiredBetSlip() {
        CustomerDto customer = new CustomerDto(1L, "Kemal", "Efe");
        BetSlipDto betSlipDto = getBetSlipDto();
        String inquiryId = "test-inquiry";

        EventDto eventDto = getEventDto();
        when(eventBettingOddsUpdatingService.getEvent(10L)).thenReturn(eventDto);

        betSlipService.initializeBetSlip(customer, betSlipDto);

        assertThatThrownBy(() -> betSlipService.finalizeBetSlip(customer, inquiryId))
                .isInstanceOf(BetSlipExpiredException.class)
                .hasMessage("Bet slip has been expired, please initialize a new bet slip.");
    }

    private static BetSlipDto getBetSlipDto() {
        BetSlipDto betSlipDto = new BetSlipDto();
        betSlipDto.setBetType(BetType.DRAW);
        betSlipDto.setBetOdds(new BigDecimal("2.21"));
        betSlipDto.setBetAmount(new BigDecimal("2500"));
        betSlipDto.setEventId(10L);
        betSlipDto.setCurrencyCode("TL");
        betSlipDto.setCouponCount(1);
        return betSlipDto;
    }

    private static EventDto getEventDto() {
        EventDto event = new EventDto();
        event.setId(10L);
        event.setHomeTeam("Fenerbahçe");
        event.setAwayTeam("Galatasaray");
        event.setLeagueName("Turkish Super League");
        event.setBetOddsDto(new BetOddsDto(1L, BigDecimal.valueOf(1.5), BigDecimal.valueOf(2.21), BigDecimal.valueOf(1.45)));
        event.setMatchStartTime(LocalDateTime.of(2025, 7, 13, 21, 45));
        return event;
    }
}

