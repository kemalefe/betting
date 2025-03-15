package com.bilyoner.betting.infrastructure.http;

import com.bilyoner.betting.contract.ErrorDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(Exception e, HttpServletRequest httpRequest) {

        ErrorDto error = new ErrorDto(ExceptionUtils.getStackTrace(e));
        error.setErrorCode("EC001"); // could be customized or expanded
        error.setErrorMessage(e.getMessage());

        log.error("Exception occurred", e);

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}