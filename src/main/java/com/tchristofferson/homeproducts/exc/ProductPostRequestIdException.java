package com.tchristofferson.homeproducts.exc;

public class ProductPostRequestIdException extends PostRequestIdException {
    public ProductPostRequestIdException() {
        super("Can't specify a product id for post requests!");
    }
}
