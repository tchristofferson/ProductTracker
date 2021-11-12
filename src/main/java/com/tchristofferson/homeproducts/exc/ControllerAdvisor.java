package com.tchristofferson.homeproducts.exc;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler({InvalidCategoryIdException.class, InvalidPropertyIdException.class, InvalidPropertyLocationIdException.class, InvalidProductId.class})
    public ResponseEntity<String> handleInvalidIdException(InvalidIdException invalidIdException) {
        return new ResponseEntity<>(invalidIdException.getMessage(), invalidIdException.getStatus());
    }

    @ExceptionHandler({UnspecifiedCategoryIdException.class, UnspecifiedPropertyIdException.class, UnspecifiedPropertyLocationIdException.class})
    public ResponseEntity<String> handleUnspecifiedIdException(UnspecifiedIdException unspecifiedIdException) {
        return new ResponseEntity<>(unspecifiedIdException.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException() {
        return new ResponseEntity<>("Possible invalid id!", HttpStatus.BAD_REQUEST);
    }

}
