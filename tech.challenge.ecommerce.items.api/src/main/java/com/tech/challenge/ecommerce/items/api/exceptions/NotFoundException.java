package com.tech.challenge.ecommerce.items.api.exceptions;

public class NotFoundException extends RuntimeException {


    public NotFoundException(String msg) {
        super(msg);
    }
}