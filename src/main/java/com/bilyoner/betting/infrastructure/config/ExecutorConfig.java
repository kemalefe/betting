package com.bilyoner.betting.infrastructure.config;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(BettingConfig.class)
public class ExecutorConfig {

    private final BettingConfig bettingConfig;
    private ExecutorService consumerExecutor;
    private ExecutorService producerExecutor;

    @Bean
    public ExecutorService consumerExecutor() {
        consumerExecutor = Executors.newFixedThreadPool(bettingConfig.getConsumer().getThreads());
        return consumerExecutor;
    }

    @Bean
    public ExecutorService producerExecutor() {
        producerExecutor = Executors.newFixedThreadPool(bettingConfig.getProducer().getThreads());
        return producerExecutor;
    }

    @PreDestroy
    public void shutdownExecutor() {
        consumerExecutor.shutdown();
        producerExecutor.shutdown();
    }
}