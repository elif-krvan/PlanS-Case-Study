package com.plans.core.response;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.plans.core.exception.CustomException;

import jakarta.servlet.http.HttpServletResponse;

public class Response {
    
    private final static ObjectMapper objectMapper = new ObjectMapper();

    private static HashMap<String, Object> createMap(String message, HttpStatus status) {
        HashMap<String, Object> body = new HashMap<String, Object>();
        String timestamp = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss").format(new java.util.Date());

        body.put("timestamp", timestamp);
        body.put("status", status.value());
        body.put("msg", message);        

        return body;
    }

    private static HashMap<String, Object> createMap(String message, HttpStatus status, Object data) {
        HashMap<String, Object> body = createMap(message, status);
        body.put("data", data);      

        return body;
    }
    
    public static ResponseEntity<Object> create(String message, HttpStatus status, Object data) {
        return new ResponseEntity<Object>(createMap(message, status, data), status);
    }

    public static ResponseEntity<Object> create(String message, HttpStatus status) {
        return new ResponseEntity<Object>(createMap(message, status), status);
    }

    private static String createString(CustomException ex) {
        try {
            return objectMapper.writeValueAsString(createMap(ex.getMessage(), ex.getStatus()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Something went wrong";
        }
    }
    
    private static String createString(String message, HttpStatus status) {
        try {
            return objectMapper.writeValueAsString(createMap(message, status));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Something went wrong";
        }
    } 

    public static void createServletError(CustomException e, HttpServletResponse response) throws IOException {
        response.setStatus(e.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(createString(e));
        response.flushBuffer();
    }
    
    public static void createServletError(String msg, HttpStatus status, HttpServletResponse response) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(createString(msg, status));
        response.flushBuffer();
    }
}