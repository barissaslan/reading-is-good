package com.barisaslan.readingisgood.config.security;

public class SecurityConstants {
    private SecurityConstants() {
    }

    public static final String SECRET = "reading";
    public static final long AUTH_EXPIRATION_TIME = 423_000_000; // 5 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
