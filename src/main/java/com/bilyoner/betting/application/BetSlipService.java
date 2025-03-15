package com.bilyoner.betting.application;

import com.bilyoner.betting.contract.BetSlipDto;
import com.bilyoner.betting.contract.BetSlipFinalizeResponse;
import com.bilyoner.betting.contract.BetSlipInitializeResponse;
import com.bilyoner.betting.domain.exception.BetSlipExpiredException;

public interface BetSlipService {

    BetSlipInitializeResponse initializeBetSlip(BetSlipDto betSlip);

    BetSlipFinalizeResponse finalizeBetSlip(String uuid) throws BetSlipExpiredException;
}
