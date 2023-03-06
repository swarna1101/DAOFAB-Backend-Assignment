package com.assignment.daofab.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 
 * @author Sanket Lathiya
 *
 */
@ControllerAdvice
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(ParentTransactionFetchingException.class)
    public ResponseEntity<String> handleParentTransactionFetchingException(ParentTransactionFetchingException exc) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exc.getMessage());
    }
    
    @ExceptionHandler(InvalidParentIdException.class)
    public ResponseEntity<String> handleInvalidParentIdException(InvalidParentIdException exc) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exc.getMessage());
    }
    
    @ExceptionHandler(ChildTransactionFetchingException.class)
    public ResponseEntity<String> handleChildTransactionFetchingException(ChildTransactionFetchingException exc) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exc.getMessage());
    }
}