package com.plans.core.response;

import jakarta.servlet.http.Cookie;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RRefreshToken {
    private Cookie accessToken;
}
