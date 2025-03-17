package com.bilyoner.betting.protocol;

import com.bilyoner.betting.application.BetSlipService;
import com.bilyoner.betting.contract.BetSlipDto;
import com.bilyoner.betting.contract.BetSlipFinalizeResponse;
import com.bilyoner.betting.contract.BetSlipInitializeResponse;
import com.bilyoner.betting.domain.core.Customer;
import com.bilyoner.betting.domain.core.CustomerDto;
import com.bilyoner.betting.domain.exception.BetSlipExpiredException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api/bet-slip", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class BetSlipController {

    private final BetSlipService betSlipService;

    @Operation(
            summary = "Initialize Bet Slip",
            description = "This endpoint initializes a bet slip with the provided customer and bet slip data.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully initialized bet slip",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BetSlipInitializeResponse.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content(mediaType = "application/json"))
            }
    )
    @PostMapping(path = "/initialize")
    public BetSlipInitializeResponse initializeBetSlip(@Parameter(hidden = true) @Customer CustomerDto customer, @Valid @RequestBody BetSlipDto betSlip) {
        return betSlipService.initializeBetSlip(customer, betSlip);
    }

    @Operation(
            summary = "Finalize Bet Slip",
            description = "This endpoint finalizes the bet slip based on the given inquiry ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully finalized bet slip",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BetSlipFinalizeResponse.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Bet slip not found", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "410", description = "Bet slip expired", content = @Content(mediaType = "application/json"))
            }
    )
    @GetMapping(path = "/finalize/{inquiryId}")
    public BetSlipFinalizeResponse finalizeBetSlip(@Parameter(hidden = true) @Customer CustomerDto customer, @NotNull @PathVariable String inquiryId) throws BetSlipExpiredException {
        return betSlipService.finalizeBetSlip(customer, inquiryId);
    }
}
