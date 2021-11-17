package com.tchristofferson.homeproducts.exc;

public class PropertyPostRequestIdException extends PostRequestIdException {
    public PropertyPostRequestIdException() {
        super("Can't specify a property id for post requests!");
    }
}
