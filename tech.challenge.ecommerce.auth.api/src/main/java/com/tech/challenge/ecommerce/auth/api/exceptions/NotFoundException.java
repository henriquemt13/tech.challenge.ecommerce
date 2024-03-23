package com.tech.challenge.ecommerce.auth.api.exceptions;

public class NotFoundException extends RuntimeException {


    public NotFoundException(String msg) {
        super(msg);
    }
}