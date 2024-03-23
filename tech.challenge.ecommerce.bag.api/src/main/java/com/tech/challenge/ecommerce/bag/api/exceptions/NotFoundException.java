package com.tech.challenge.ecommerce.bag.api.exceptions;

public class NotFoundException extends RuntimeException {


    public NotFoundException(String msg) {
        super(msg);
    }
}