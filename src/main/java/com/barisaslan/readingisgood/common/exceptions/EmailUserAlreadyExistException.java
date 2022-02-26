package com.barisaslan.readingisgood.common.exceptions;

public class EmailUserAlreadyExistException extends Exception {
    public EmailUserAlreadyExistException() {
        super("Email User already exists!");
    }
}
