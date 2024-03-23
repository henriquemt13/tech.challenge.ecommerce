package com.tech.challenge.ecommerce.payment.api.exceptions;

public class NotFoundException extends RuntimeException {


    public NotFoundException(String msg) {
        super(msg);
    }
}