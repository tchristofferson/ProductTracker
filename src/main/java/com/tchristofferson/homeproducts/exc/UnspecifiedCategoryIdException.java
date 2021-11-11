package com.tchristofferson.homeproducts.exc;

public class UnspecifiedCategoryIdException extends UnspecifiedIdException {

    public UnspecifiedCategoryIdException() {
        super("Unspecified category id!");
    }
}
