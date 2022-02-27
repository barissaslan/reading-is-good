package com.barisaslan.readingisgood.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.barisaslan.readingisgood.common.constants.Constants.CONFLICT_RESPONSE_MESSAGE;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<ErrorResponse> handleException(OptimisticLockingFailureException exception) {
        log.error("OptimisticLockingFailureException! Error: " + exception.getMessage());
        return new ResponseEntity<>(new ErrorResponse(CONFLICT_RESPONSE_MESSAGE), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exception(Exception exception) {
        log.error("Unhandled exception occurred: " + exception.getMessage());
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
