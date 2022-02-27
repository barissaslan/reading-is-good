package com.barisaslan.readingisgood.common.exceptions;

public class OutOfStockException extends Exception {
    public OutOfStockException() {
        super("Out of stock!");
    }
}
