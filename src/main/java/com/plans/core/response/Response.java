package com.plans.core.response;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.Cookie;

public class Response {

    public static ResponseEntity<Object> create(String message, HttpStatus status, Object data) {
        HashMap<String, Object> response = new HashMap<String, Object>();
        String timestamp = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss").format(new java.util.Date());

        response.put("data", data);
        response.put("timestamp", timestamp);
        response.put("status", status.value());
        response.put("msg", message);        

        return new ResponseEntity<Object>(response, status);
    }

    public static ResponseEntity<Object> create(String message, HttpStatus status) {
        HashMap<String, Object> response = new HashMap<String, Object>();
        String timestamp = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss").format(new java.util.Date());

        response.put("timestamp", timestamp);
        response.put("msg", message);
        response.put("status", status.value());

        return new ResponseEntity<Object>(response, status);
    }
}