package com.bilyoner.betting.application;

import com.bilyoner.betting.contract.BetSlipDto;
import com.bilyoner.betting.contract.BetSlipFinalizeResponse;
import com.bilyoner.betting.contract.BetSlipInitializeResponse;
import com.bilyoner.betting.domain.core.CustomerDto;
import com.bilyoner.betting.domain.exception.BetSlipExpiredException;

public interface BetSlipService {

    BetSlipInitializeResponse initializeBetSlip(CustomerDto customer, BetSlipDto betSlip);

    BetSlipFinalizeResponse finalizeBetSlip(CustomerDto customer, String inquiryId) throws BetSlipExpiredException;
}
