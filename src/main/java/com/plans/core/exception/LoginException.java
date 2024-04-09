package com.plans.core.exception;

import org.springframework.http.HttpStatus;

public class LoginException extends CustomException {
    public LoginException() {
        super("Username or password is wrong!", HttpStatus.UNAUTHORIZED);
    }
}
