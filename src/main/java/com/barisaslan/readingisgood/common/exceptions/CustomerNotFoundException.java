package com.barisaslan.readingisgood.common.exceptions;

public class CustomerNotFoundException extends Exception {
    public CustomerNotFoundException() {
        super("Customer not found.");
    }
}
