package com.bilyoner.betting;

import com.bilyoner.betting.infrastructure.config.BettingConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(BettingConfig.class)
public class BettingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BettingApplication.class, args);
    }
}
