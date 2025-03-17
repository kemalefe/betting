package com.bilyoner.betting.contract;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BetSlipInitializeResponse {

    private String inquiryId;
    private boolean betOddsChanged;
    private Long timeoutSeconds;
    private BetSlipDto betSlipDto;
}