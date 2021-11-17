package com.tchristofferson.homeproducts.exc;

public class PropertyLocationPostRequestIdException extends PostRequestIdException {
    public PropertyLocationPostRequestIdException() {
        super("Can't specify a property location id for post requests!");
    }
}
