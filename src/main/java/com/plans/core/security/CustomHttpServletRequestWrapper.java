package com.plans.core.security;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

// The author of this code is ChatGPT
public class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final Map<String, String> customHeaders;

    public CustomHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        this.customHeaders = new HashMap<>();
    }

    public void addHeader(String name, String value) {
        this.customHeaders.put(name, value);
    }

    @Override
    public String getHeader(String name) {
        String headerValue = customHeaders.get(name);
        if (headerValue != null) {
            return headerValue;
        }
        return super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return Collections.enumeration(customHeaders.keySet());
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        Enumeration<String> originalHeaders = super.getHeaders(name);
        String customHeaderValue = customHeaders.get(name);
        if (customHeaderValue != null) {
            return Collections.enumeration(Collections.singletonList(customHeaderValue));
        }
        return originalHeaders;
    }
}

