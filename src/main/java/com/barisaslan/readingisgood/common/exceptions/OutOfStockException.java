package com.barisaslan.readingisgood.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class OutOfStockException extends Exception {
    public OutOfStockException() {
        super("There is not enough stock.");
    }
}
