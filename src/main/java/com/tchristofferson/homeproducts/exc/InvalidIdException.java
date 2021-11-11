package com.tchristofferson.homeproducts.exc;

import org.springframework.http.HttpStatus;

public class InvalidIdException extends RuntimeException {

    private final HttpStatus status;

    protected InvalidIdException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
