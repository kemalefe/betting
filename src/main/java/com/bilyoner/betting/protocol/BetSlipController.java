package com.bilyoner.betting.protocol;

import com.bilyoner.betting.application.BetSlipService;
import com.bilyoner.betting.contract.BetSlipDto;
import com.bilyoner.betting.contract.BetSlipFinalizeResponse;
import com.bilyoner.betting.contract.BetSlipInitializeResponse;
import com.bilyoner.betting.domain.exception.BetSlipExpiredException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api/bet-slip", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class BetSlipController {

    private final BetSlipService betSlipService;

    @PostMapping(path = "/initialize")
    public BetSlipInitializeResponse initializeBetSlip(@RequestBody BetSlipDto betSlip) {
        return betSlipService.initializeBetSlip(betSlip);
    }

    @PostMapping(path = "/finalize/{id}")
    public BetSlipFinalizeResponse finalizeBetSlip(@PathVariable String id) throws BetSlipExpiredException {
        return betSlipService.finalizeBetSlip(id);
    }
}
