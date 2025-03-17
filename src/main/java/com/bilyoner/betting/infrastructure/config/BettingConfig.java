package com.bilyoner.betting.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.math.BigDecimal;

@Getter
@Setter
@ConfigurationProperties(prefix = "betting")
public class BettingConfig {

    Long betFinalizeTimeout;
    Integer maxCouponCount;
    BigDecimal maxTotalBetAmount;

    @NestedConfigurationProperty
    OddsConfig odds;

    @NestedConfigurationProperty
    ProducerConsumerConfig producer;

    @NestedConfigurationProperty
    ProducerConsumerConfig consumer;

    @NestedConfigurationProperty
    BulletinConfig bulletin;

    @Getter
    @Setter
    public static class OddsConfig {
        Double minValue;
        Double maxValue;
        Long updateInterval;
    }

    @Getter
    @Setter
    public static class ProducerConsumerConfig {
        Integer prefetchCount;
        Integer threads;
    }

    @Getter
    @Setter
    public static class BulletinConfig {
        Integer duration;
    }
}
