package com.tchristofferson.homeproducts.exc;

import org.springframework.http.HttpStatus;

public class InvalidPropertyIdException extends InvalidIdException {

    public InvalidPropertyIdException(HttpStatus status) {
        super("Invalid property id!", status);
    }
}
