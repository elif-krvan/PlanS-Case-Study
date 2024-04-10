package com.plans.core.response;

import com.plans.core.model.User;

import jakarta.servlet.http.Cookie;
import lombok.Data;

@Data
public class RLoginUser {
    private RUser user;
    private Cookie accessToken;
    private Cookie refreshToken;

    public RLoginUser(User user, Cookie accessToken, Cookie refreshToken) {
        this.user = new RUser(user);
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
