package com.barisaslan.readingisgood.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CustomerNotFoundException extends Exception {
    public CustomerNotFoundException() {
        super("Customer not found.");
    }
}
