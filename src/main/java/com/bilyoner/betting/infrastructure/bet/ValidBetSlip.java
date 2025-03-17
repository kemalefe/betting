package com.bilyoner.betting.infrastructure.bet;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BetSlipValidator.class)
public @interface ValidBetSlip {
    String message() default "Total bet amount exceeds the allowed limit.";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}