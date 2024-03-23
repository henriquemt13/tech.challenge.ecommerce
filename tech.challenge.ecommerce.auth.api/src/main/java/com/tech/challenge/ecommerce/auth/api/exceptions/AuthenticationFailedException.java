package com.tech.challenge.ecommerce.auth.api.exceptions;

public class AuthenticationFailedException extends RuntimeException {

    public AuthenticationFailedException() {
        super("Authentication failed");
    }

    public AuthenticationFailedException(String msg) {
        super(msg);
    }
}