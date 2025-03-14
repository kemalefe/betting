package com.bilyoner.betting.protocol;

import com.bilyoner.betting.application.EventBettingOddsUpdatingService;
import com.bilyoner.betting.contract.BulletinResponse;
import com.bilyoner.betting.infrastructure.config.BettingConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class BulletinController {

    private final BettingConfig bettingConfig;
    private final EventBettingOddsUpdatingService eventService;

    @GetMapping(value = "/bulletin", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<BulletinResponse> streamBulletin() {
        return Flux.interval(Duration.ofSeconds(bettingConfig.getBulletin().getDuration()))
                .map(sequence -> new BulletinResponse(eventService.getAllEvents()));
    }
}
