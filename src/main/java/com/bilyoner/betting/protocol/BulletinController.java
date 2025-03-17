package com.bilyoner.betting.protocol;

import com.bilyoner.betting.application.EventBettingOddsUpdatingService;
import com.bilyoner.betting.contract.BulletinResponse;
import com.bilyoner.betting.infrastructure.config.BettingConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@RequestMapping(path = "/api/bulletin")
@RequiredArgsConstructor
public class BulletinController {

    private final BettingConfig bettingConfig;
    private final EventBettingOddsUpdatingService eventService;

    @Operation(
            summary = "Stream Bulletin Updates",
            description = "This endpoint streams real-time bulletin updates. It provides updates at a regular interval based on the configured duration.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully streamed bulletin updates",
                            content = @Content(mediaType = MediaType.TEXT_EVENT_STREAM_VALUE, schema = @Schema(implementation = BulletinResponse.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json"))
            }
    )
    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<BulletinResponse> streamBulletin() {
        return Flux.interval(Duration.ofSeconds(bettingConfig.getBulletin().getDuration()))
                .map(sequence -> new BulletinResponse(eventService.getAllEvents()));
    }
}
