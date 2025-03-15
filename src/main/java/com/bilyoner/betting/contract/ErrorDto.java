package com.bilyoner.betting.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorDto {
    private String errorCode;
    private String errorMessage;
    @JsonIgnore
    private String stackTrace;

    public ErrorDto(String stackTrace) {
        this.stackTrace = stackTrace;
    }
}
