package com.bilyoner.betting.infrastructure.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class ExecutorConfig {

    private final BettingConfig bettingConfig;

    @Bean
    public ExecutorService consumerExecutor() {
        return Executors.newFixedThreadPool(bettingConfig.getConsumer().getThreads());
    }

    @Bean
    public ExecutorService producerExecutor() {
        return Executors.newFixedThreadPool(bettingConfig.getProducer().getThreads());
    }
}