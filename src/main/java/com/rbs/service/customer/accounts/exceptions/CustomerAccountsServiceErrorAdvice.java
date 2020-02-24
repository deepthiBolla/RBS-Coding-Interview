package com.rbs.service.customer.accounts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class CustomerAccountsServiceErrorAdvice {

    Logger logger = LogManager.getLogger(CustomerAccountsServiceErrorAdvice.class);

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<String> handleRunTimeException(RuntimeException e) {
        return error(INTERNAL_SERVER_ERROR, e);
    }
    @ExceptionHandler({CustomerAccountNotFoundException.class})
    public ResponseEntity<String> handleNotFoundException(CustomerAccountNotFoundException e) {
        return error(NOT_FOUND, e);
    }
    @ExceptionHandler({CustomerAccountServiceException.class})
    public ResponseEntity<String> handleCustomerAccountServiceException(CustomerAccountServiceException e){
        return error(INTERNAL_SERVER_ERROR, e);
    }
    private ResponseEntity<String> error(HttpStatus status, Exception e) {
        logger.error("Exception : ", e);
        return ResponseEntity.status(status).body(e.getMessage());
    }
}
