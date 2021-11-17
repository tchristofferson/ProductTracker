package com.tchristofferson.homeproducts.exc;

public class CategoryPostRequestIdException extends PostRequestIdException {
    public CategoryPostRequestIdException() {
        super("Can't specify a category id for post requests!");
    }
}
