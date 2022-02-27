package com.barisaslan.readingisgood.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class EmailUserAlreadyExistException extends Exception {
    public EmailUserAlreadyExistException() {
        super("Email User already exists!");
    }
}
