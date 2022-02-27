package com.barisaslan.readingisgood.common.exceptions;

public class OutOfStockException extends Exception {
    public OutOfStockException() {
        super("There is not enough stock.");
    }
}
