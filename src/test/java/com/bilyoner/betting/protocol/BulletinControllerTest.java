package com.bilyoner.betting.protocol;

import com.bilyoner.betting.application.EventBettingOddsUpdatingService;
import com.bilyoner.betting.contract.BulletinResponse;
import com.bilyoner.betting.contract.EventDto;
import com.bilyoner.betting.infrastructure.config.BettingConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@WebFluxTest(BulletinController.class)
@ExtendWith(MockitoExtension.class)
class BulletinControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private BettingConfig bettingConfig;

    @MockitoBean
    private EventBettingOddsUpdatingService eventService;

    @Test
    void shouldStreamBulletin() {

        List<EventDto> events = List.of(new EventDto());

        BettingConfig.BulletinConfig bulletinConfig = mock(BettingConfig.BulletinConfig.class);
        when(bettingConfig.getBulletin()).thenReturn(bulletinConfig);
        when(bulletinConfig.getDuration()).thenReturn(1);

        when(eventService.getAllEvents()).thenReturn(events);

        webTestClient.get()
                .uri("/api/bulletin")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM)
                .returnResult(BulletinResponse.class)
                .getResponseBody()
                .take(3)
                .collectList()
                .as(StepVerifier::create)
                .expectNextMatches(responses -> responses.size() == 3)
                .verifyComplete();
    }
}
