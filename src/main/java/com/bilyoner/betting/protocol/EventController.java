package com.bilyoner.betting.protocol;

import com.bilyoner.betting.application.EventBettingOddsUpdatingService;
import com.bilyoner.betting.contract.EventDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api/events", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class EventController {

    private final EventBettingOddsUpdatingService eventService;

    @Operation(
            summary = "Get All Events",
            description = "This endpoint retrieves a list of all events.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved all events",
                            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = EventDto.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json"))
            }
    )
    @GetMapping
    public List<EventDto> getAllEvents() {
        return eventService.getAllEvents();
    }

    @Operation(
            summary = "Get Event by ID",
            description = "This endpoint retrieves a specific event by its ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved the event",
                            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = EventDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Event not found",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json"))
            }
    )
    @GetMapping("/{id}")
    public EventDto getEvent(@PathVariable Long id) {
        return eventService.getEvent(id);
    }

    @Operation(
            summary = "Add New Event",
            description = "This endpoint adds a new event to the system.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Event successfully created",
                            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = EventDto.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json"))
            }
    )
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public EventDto add(@RequestBody EventDto event) {
        return eventService.addEvent(event);
    }
}
