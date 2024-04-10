package com.plans.core.exception;

import org.springframework.http.HttpStatus;

public class AlreadyFoundException extends CustomException {   
    public AlreadyFoundException(String msg) {
        super(msg, HttpStatus.BAD_REQUEST);
    }
}
