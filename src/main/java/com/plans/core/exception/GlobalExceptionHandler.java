package com.plans.core.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.plans.core.response.Response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler(LoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ResponseEntity<Object> handleLoginException(LoginException ex) {
        return Response.create(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SomethingWentWrongException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<Object> handleSomethingWentWrongException(SomethingWentWrongException ex) {
        return Response.create(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Object> handleCustomException(CustomException ex) {
        return Response.create(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Handle 400 Bad Request (Invalid request body)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException e) {
        
        // Get validation errors
        List<String> validationErrors = e.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(fieldError -> getFailedValidationMessage(fieldError))
            .collect(Collectors.toList());

        return Response.create("Request body is invalid!", HttpStatus.BAD_REQUEST, validationErrors);
    }

    // Handle 405 Method Not Allowed (Incorrect request type)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<Object> handleMethodNotAllowedException(HttpRequestMethodNotSupportedException e) {
        return Response.create("Incorrect request method!", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<Object> handleInvalidEnumType(HttpMessageNotReadableException ex) {
        log.error(ex.getMessage());

        return Response.create("Bad Request", HttpStatus.BAD_REQUEST);
    }

    private String getFailedValidationMessage(FieldError fieldError) {
        // Extract the specific validation message that caused the failure
        String fieldName = fieldError.getField();
        String defaultMessage = fieldError.getDefaultMessage();

        StringBuilder errorMsg = new StringBuilder();

            errorMsg.append(fieldName);
            errorMsg.append(" ");
            errorMsg.append(defaultMessage);

        return errorMsg.toString();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        return Response.create(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

