package com.rbs.service.customer.accounts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomerAccountNotFoundException extends RuntimeException {

    public CustomerAccountNotFoundException(String message) {
        super(message);
    }
}