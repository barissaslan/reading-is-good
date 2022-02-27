package com.barisaslan.readingisgood.common.exceptions;

public class BookNotFoundException extends Exception {
    public BookNotFoundException() {
        super("Book not found!");
    }
}
