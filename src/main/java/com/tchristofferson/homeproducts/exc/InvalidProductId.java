package com.tchristofferson.homeproducts.exc;

import org.springframework.http.HttpStatus;

public class InvalidProductId extends InvalidIdException {

    public InvalidProductId(HttpStatus status) {
        super("Invalid product id!", status);
    }
}
