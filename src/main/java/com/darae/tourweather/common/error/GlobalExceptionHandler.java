package com.darae.tourweather.common.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.darae.tourweather.tourspot.TourSpotNotFoundException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolation(
            ConstraintViolationException exception
    ) {
        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "요청 값이 올바르지 않습니다."
        );
    }

    @ExceptionHandler(TourSpotNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleTourSpotNotFound(
            TourSpotNotFoundException exception
    ) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage()
        );
    }
}
