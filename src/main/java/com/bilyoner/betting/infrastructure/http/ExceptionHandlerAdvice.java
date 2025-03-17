package com.bilyoner.betting.infrastructure.http;

import com.bilyoner.betting.contract.ErrorDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerAdvice {

    private static void logException(Exception e) {
        log.error("Exception occurred", e);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(Exception e, HttpServletRequest httpRequest) {

        ErrorDto error = new ErrorDto("EC001", e.getMessage());
        logException(e);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleException(MethodArgumentNotValidException e, HttpServletRequest httpRequest) {

        var fieldError = e.getFieldError();
        ErrorDto error = new ErrorDto("EC002", fieldError.getField() + " " + fieldError.getDefaultMessage());
        logException(e);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorDto> handleException(MissingRequestHeaderException e, HttpServletRequest httpRequest) {

        ErrorDto error = new ErrorDto("EC003", e.getMessage());
        logException(e);
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorDto> handleException(NoResourceFoundException e, HttpServletRequest httpRequest) {

        ErrorDto error = new ErrorDto("EC004", e.getMessage());
        logException(e);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}