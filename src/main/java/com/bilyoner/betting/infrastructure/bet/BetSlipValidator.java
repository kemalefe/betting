package com.bilyoner.betting.infrastructure.bet;

import com.bilyoner.betting.contract.BetSlipDto;
import com.bilyoner.betting.infrastructure.config.BettingConfig;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class BetSlipValidator implements ConstraintValidator<ValidBetSlip, BetSlipDto> {

    private final BettingConfig bettingConfig;
    private final Validator validator;


    @Override
    public boolean isValid(BetSlipDto betSlip, ConstraintValidatorContext context) {

        if (betSlip.getBetAmount() == null || betSlip.getEventId() == null) {
            return false;
        }

        if (betSlip.getCouponCount() > bettingConfig.getMaxCouponCount()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Coupon count limit exceeded: Maximum allowed is %s.".formatted(bettingConfig.getMaxCouponCount()))
                    .addPropertyNode("couponCount")
                    .addConstraintViolation();
            return false;
        }

        BigDecimal totalBetAmount = betSlip.getBetAmount().multiply(BigDecimal.valueOf(betSlip.getCouponCount()));
        if (totalBetAmount.compareTo(bettingConfig.getMaxTotalBetAmount()) > 0) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Bet amount limit exceeded: Maximum allowed is %s.".formatted(bettingConfig.getMaxTotalBetAmount()))
                    .addPropertyNode("betAmount")
                    .addPropertyNode("couponCount")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}