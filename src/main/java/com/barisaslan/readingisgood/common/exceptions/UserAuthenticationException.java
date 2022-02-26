package com.barisaslan.readingisgood.common.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserAuthenticationException extends AuthenticationException {
    public UserAuthenticationException() {
        super("User authentication failed.");
    }
}
