package com.bilyoner.betting.application;

import com.bilyoner.betting.contract.BetSlipDto;
import com.bilyoner.betting.contract.BetSlipFinalizeResponse;
import com.bilyoner.betting.contract.BetSlipInitializeResponse;
import com.bilyoner.betting.domain.BetSlipRepository;
import com.bilyoner.betting.domain.core.CustomerDto;
import com.bilyoner.betting.domain.exception.BetSlipExpiredException;
import com.bilyoner.betting.infrastructure.config.BettingConfig;
import com.bilyoner.betting.infrastructure.mapper.BetSlipMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class BetSlipServiceImpl implements BetSlipService, InitializingBean {

    private final BettingConfig bettingConfig;
    private final EventBettingOddsUpdatingService eventBettingOddsUpdatingService;
    private final BetSlipRepository betSlipRepository;
    private final BetSlipMapper betSlipMapper;

    private Cache<String, BetSlipDto> betSlipCaffeine;

    @Override
    public void afterPropertiesSet() {
        loadCache();
    }

    public void loadCache() {
        betSlipCaffeine = Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build();
    }

    @Override
    public BetSlipInitializeResponse initializeBetSlip(CustomerDto customer, BetSlipDto betSlip) {

        var event = eventBettingOddsUpdatingService.getEvent(betSlip.getEventId());

        var eventBetOdds = event.getBetOdds(betSlip.getBetType());
        var betOdds = betSlip.getBetOdds();

        var betOddsChanges = !eventBetOdds.equals(betOdds);
        betOdds = eventBetOdds;

        betSlip.setBetOdds(betOdds);
        betSlip.setTimestamp(System.currentTimeMillis());

        String inquiryId = UUID.randomUUID().toString();
        betSlipCaffeine.put(inquiryId, betSlip);

        return new BetSlipInitializeResponse(inquiryId, betOddsChanges, bettingConfig.getBetFinalizeTimeout(), betSlip);
    }

    @Override
    public BetSlipFinalizeResponse finalizeBetSlip(CustomerDto customer, String inquiryId) throws BetSlipExpiredException {

        BetSlipDto betSlipDto = betSlipCaffeine.getIfPresent(inquiryId);
        if (betSlipDto == null)
            throw new BetSlipExpiredException("Bet slip has been expired, please initialize a new bet slip.");

        var betSlipTimestamp = betSlipDto.getTimestamp();
        var currentTimestamp = System.currentTimeMillis();
        var maximumAllowedDifference = bettingConfig.getBetFinalizeTimeout() * 1000L;

        if (currentTimestamp - betSlipTimestamp > maximumAllowedDifference) {
            betSlipCaffeine.invalidate(inquiryId);
            var initializeBetSlip = initializeBetSlip(customer, new BetSlipDto(betSlipDto));
            return new BetSlipFinalizeResponse(initializeBetSlip);
        }

        var betSlip = betSlipMapper.toEntity(betSlipDto, customer.getId());
        betSlip = betSlipRepository.save(betSlip);
        betSlipDto = betSlipMapper.toDto(betSlip);

        betSlipCaffeine.invalidate(inquiryId);
        return new BetSlipFinalizeResponse(true, betSlipDto);
    }
}
