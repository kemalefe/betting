package com.bilyoner.betting.infrastructure.http;

import com.bilyoner.betting.contract.ErrorDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleException(MethodArgumentNotValidException e, HttpServletRequest httpRequest) {

        ErrorDto error = new ErrorDto(ExceptionUtils.getStackTrace(e));
        error.setErrorCode("EC002"); // could be customized or expanded
        error.setErrorMessage(e.getMessage());

        log.error("Exception occurred", e);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorDto> handleException(MissingRequestHeaderException e, HttpServletRequest httpRequest) {

        ErrorDto error = new ErrorDto(ExceptionUtils.getStackTrace(e));
        error.setErrorCode("EC003"); // could be customized or expanded
        error.setErrorMessage(e.getMessage());

        log.error("Exception occurred", e);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorDto> handleException(NoResourceFoundException e, HttpServletRequest httpRequest) {

        ErrorDto error = new ErrorDto(ExceptionUtils.getStackTrace(e));
        error.setErrorCode("EC004"); // could be customized or expanded
        error.setErrorMessage(e.getMessage());

        log.error("Exception occurred", e);

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}