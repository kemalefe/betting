package com.bilyoner.betting.contract;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BetSlipFinalizeResponse extends BetSlipInitializeResponse {

    private boolean success;

    public BetSlipFinalizeResponse (boolean success, BetSlipDto betSlipDto) {
        this.setSuccess(success);
        this.setBetSlipDto(betSlipDto);
    }

    public BetSlipFinalizeResponse(BetSlipInitializeResponse betSlipInitializeResponse) {
        this.success = false;
        this.setBetSlipDto(betSlipInitializeResponse.getBetSlipDto());
        this.setUuid(betSlipInitializeResponse.getUuid());
        this.setBetOddsChanged(betSlipInitializeResponse.isBetOddsChanged());
        this.setTimeoutSeconds(betSlipInitializeResponse.getTimeoutSeconds());
    }
}
