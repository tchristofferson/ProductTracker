package com.tchristofferson.homeproducts.exc;

import org.springframework.http.HttpStatus;

public class InvalidCategoryIdException extends InvalidIdException {

    public InvalidCategoryIdException(HttpStatus status) {
        super("Invalid category id!", status);
    }
}
