package com.tchristofferson.homeproducts.exc;

import org.springframework.http.HttpStatus;

public class InvalidPropertyLocationIdException extends InvalidIdException {

    public InvalidPropertyLocationIdException(HttpStatus status) {
        super("Invalid property location id!", status);
    }
}
