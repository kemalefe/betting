package com.bilyoner.betting.protocol;

import com.bilyoner.betting.application.EventService;
import com.bilyoner.betting.contract.BulletinResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class BulletinController {

    @Value("${bulletin.duration:1}")
    private int durationSeconds;

    private final EventService eventService;

    @GetMapping(value = "/bulletin", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<BulletinResponse> streamBulletin() {
        return Flux.interval(Duration.ofSeconds(durationSeconds))
                .map(sequence -> new BulletinResponse(eventService.fetchEvents()));
    }

    @GetMapping(value = "/test", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public String test() {
        return "TEST";
    }
}
